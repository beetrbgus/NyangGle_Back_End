package com.nyanggle.nyangmail.oauth;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ProviderUser {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String userId;
    private String email;
    private String nickname;
    private String domesticId; // provider 내 에서 유니크한 아이디
    private String providerType;

    private ProviderUser() {

    }
    public ProviderUser(Map<String, Object> attributes,
                            String nameAttributeKey, String userId, String nickname,
                            String email, String domesticId, String providerType) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
        this.domesticId = domesticId;
        this.providerType = providerType;
    }
    public static ProviderUser of(String registrationId,
                                      String userNameAttributeName, String userId,
                                      Map<String, Object> attributes) {
        if ("kakao".equals(registrationId)) {
            return ofKakao(userNameAttributeName, userId, attributes);
        }
        return ofGoogle(userNameAttributeName, userId, attributes);
    }

    private static ProviderUser ofGoogle(String userNameAttributeName, String userId,
                                             Map<String, Object> attributes) {
        return new ProviderUser(attributes, userNameAttributeName, userId, (String) attributes.get("name"),
                (String) attributes.get("email"), (String) attributes.get(userNameAttributeName), "google");
    }

    private static ProviderUser ofKakao(String userNameAttributeName, String userId,
                                            Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        var profile = (Map<String, Object>) kakaoAccount.get("profile");
        return new ProviderUser(attributes, userNameAttributeName, userId, (String) profile.get("nickname") //
                , (String) kakaoAccount.get("email"), String.valueOf((Long) attributes.get(userNameAttributeName))
                , "kakao");
    }}
