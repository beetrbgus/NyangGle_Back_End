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
    private String nickname;
    private String domesticId; // provider 내 에서 유니크한 아이디
    private String providerType;
    private String gender;
    private String ageRange;
    private ProviderUser() {

    }
    public ProviderUser(Map<String, Object> attributes,
                            String nameAttributeKey, String userId, String nickname,
                            String domesticId, String gender, String ageRange, String providerType) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.userId = userId;
        this.nickname = nickname;
        this.domesticId = domesticId;
        this.providerType = providerType;
        this.gender = gender;
        this.ageRange = ageRange;
    }
    public static ProviderUser of(String registrationId,
                                      String userNameAttributeName, String userId,
                                      Map<String, Object> attributes) {
        if ("kakao".equals(registrationId)) {
            return ofKakao(userNameAttributeName, userId, attributes);
        }
        return null;
    }

    private static ProviderUser ofKakao(String userNameAttributeName, String userId,
                                            Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        String age_range = "";
        String gender = "";

        if((Boolean) kakaoAccount.get("has_age_range")) {
            age_range = (String) kakaoAccount.get("age_range");
        }
        if((Boolean) kakaoAccount.get("has_gender")) {
            gender = (String) kakaoAccount.get("gender");
        }
        var profile = (Map<String, Object>) kakaoAccount.get("profile");

        return new ProviderUser(attributes, userNameAttributeName, userId, (String) profile.get("nickname")
                , String.valueOf((Long) attributes.get(userNameAttributeName))
                , gender, age_range, "kakao");
    }
}
