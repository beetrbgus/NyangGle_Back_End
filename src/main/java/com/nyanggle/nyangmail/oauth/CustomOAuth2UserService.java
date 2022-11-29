package com.nyanggle.nyangmail.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nyanggle.nyangmail.common.UserIdGenerator;
import com.nyanggle.nyangmail.exception.ErrorCode;
import com.nyanggle.nyangmail.exception.handler.NyangException;
import com.nyanggle.nyangmail.exception.user.UnAuthorizedException;
import com.nyanggle.nyangmail.interfaces.convert.OAuthInfoToUser;
import com.nyanggle.nyangmail.interfaces.dto.login.LoginRes;
import com.nyanggle.nyangmail.oauth.jwt.JwtProvider;
import com.nyanggle.nyangmail.oauth.jwt.TokenRes;
import com.nyanggle.nyangmail.persistence.entity.User;
import com.nyanggle.nyangmail.persistence.repository.CustomUserRepository;
import com.nyanggle.nyangmail.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService {
    private final UserRepository userRepository;
    private final CustomUserRepository customUserRepository;
    private final UserIdGenerator userIdGenerator;
    private final KaKaoConfigUtils kakaoConfigUtils;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final JwtProvider jwtProvider;
    private final OAuthInfoToUser oAuthUserConverter;

    private User saveOrUpdate(ProviderUser providerUser) {
        User user = customUserRepository.findByNormalUser(
                providerUser.getDomesticId(), providerUser.getProviderType()
        ).orElse(oAuthUserConverter.convert(providerUser));
        user.setLastestloginTime();
        return userRepository.save(user);
    }

    @Transactional
    public LoginRes login(String provider, String authCode) throws JsonProcessingException {
        if(!provider.equals("kakao")) {
            throw new NyangException(ErrorCode.PROVIDER_IS_UNCORRECT);
        }

        ProviderUser profile = getProfile(provider, obtainAccessToken(authCode));
        User user = saveOrUpdate(profile);
        UserPrincipal userPrincipal = UserPrincipal.create(user);
        String token = jwtProvider.generateToken(userPrincipal);
        return new LoginRes(user.getUserUid(),token,user.getDisplayName());
    }

    /**
     * 인가코드로 Access Token 받아오기
     * @param authCode
     * @return
     * @throws JsonProcessingException
     */
    private TokenRes obtainAccessToken(String authCode) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String url = kakaoConfigUtils.getTokenUrl();;
        LinkedMultiValueMap<String, String> params = kakaoConfigUtils.makeParam(authCode);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        return objectMapper.readValue(response.getBody(), TokenRes.class);
    }

    /**
     * Access Token으로 프로필 정보 가져오기
     * @param provider
     * @param tokenResponse
     * @return
     */
    public ProviderUser getProfile(String provider, TokenRes tokenResponse) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer "+tokenResponse.getAccess_token());
        String url = kakaoConfigUtils.getProfileUrl();

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        try {
            if(response.getStatusCode() == HttpStatus.OK) {
                Map<String,Object> kakaoOAuthResponseDto = objectMapper.readValue(response.getBody(), Map.class);
                return ProviderUser.of(provider,"id", userIdGenerator.userId(), kakaoOAuthResponseDto);
            }
        } catch (JsonProcessingException e) {
            throw new UnAuthorizedException();
        }
        throw new UnAuthorizedException();
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        userRequest.getClientRegistration();
        return null;
    }
}
