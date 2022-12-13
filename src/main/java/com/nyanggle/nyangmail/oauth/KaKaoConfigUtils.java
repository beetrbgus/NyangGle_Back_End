package com.nyanggle.nyangmail.oauth;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;

@Component
@Getter
public class KaKaoConfigUtils {
    @Value("${nyang.social.kakao.client_id}")
    private String clientId;    // 애플리케이션의 클라이언트 ID
    @Value("${nyang.social.kakao.client_secret}")
    private String clientSecret;
    @Value("${nyang.social.kakao.redirect}")
    private String redirectUri;
    @Value("${nyang.social.kakao.url.login}")
    private String loginUri;
    @Value("${nyang.social.kakao.url.token}")
    private String tokenUrl;
    @Value("${nyang.social.kakao.url.profile}")
    private String profileUrl;
    @Value("${nyang.social.kakao.url.logout}")
    private String logoutUrl;
    @Value("${nyang.social.kakao.admin-key}")
    private String adminKey;

    public LinkedMultiValueMap<String, String> loginParam(String code) {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);
        params.add("client_secret", clientSecret);

        return params;
    }
    public LinkedMultiValueMap<String, String> logoutParam(String domesticId) {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("target_id_type", "user_id");
        params.add("target_id", domesticId);

        return params;
    }
}
