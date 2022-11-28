package com.nyanggle.nyangmail.oauth;

import com.nyanggle.nyangmail.common.UserIdGenerator;
import com.nyanggle.nyangmail.interfaces.convert.ModelConverter;
import com.nyanggle.nyangmail.persistence.entity.User;
import com.nyanggle.nyangmail.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final UserIdGenerator userIdGenerator;
    private final DefaultOAuth2UserService defaultOAuth2UserService;
    private final ModelConverter<ProviderUser, User> userConverter;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = defaultOAuth2UserService.loadUser(userRequest);
        String providerId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                                .getProviderDetails().getUserInfoEndpoint()
                                .getUserNameAttributeName();
        String userUUId = userIdGenerator.userId();
        log.debug("OAuth2User getName   : ", oAuth2User.getName());

        ProviderUser providerUser = ProviderUser.of(providerId, userNameAttributeName, userUUId, oAuth2User.getAttributes());
        User user = saveOrUpdate(providerUser);

        return UserPrincipal.create(user);
    }

    private User saveOrUpdate(ProviderUser providerUser) {
        User user = userRepository.findByDomesticIdAndProviderType(
                providerUser.getDomesticId(), providerUser.getProviderType()
        ).orElse(userConverter.convert(providerUser));

        return userRepository.save(user);
    }
}
