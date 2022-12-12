package com.nyanggle.nyangmail.oauth.convert;

import com.nyanggle.nyangmail.oauth.dto.ProviderUser;
import com.nyanggle.nyangmail.user.persistence.Role;
import com.nyanggle.nyangmail.user.persistence.User;
import org.springframework.stereotype.Component;

@Component
public class OAuthInfoToUser implements ModelConverter<ProviderUser, User> {

    @Override
    public User convert(ProviderUser source) {
        return new User(source.getNickname(), source.getNickname(), source.getUserId(),
                source.getDomesticId(), source.getProviderType(), Role.USER, source.getGender(), source.getAgeRange());
    }
}