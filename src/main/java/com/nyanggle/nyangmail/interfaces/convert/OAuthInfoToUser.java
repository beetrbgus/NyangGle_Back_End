package com.nyanggle.nyangmail.interfaces.convert;

import com.nyanggle.nyangmail.oauth.ProviderUser;
import com.nyanggle.nyangmail.persistence.entity.Role;
import com.nyanggle.nyangmail.persistence.entity.User;
import org.springframework.stereotype.Component;

@Component
public class OAuthInfoToUser implements ModelConverter<ProviderUser, User> {

    @Override
    public User convert(ProviderUser source) {
        return new User(source.getNickname(), source.getNickname(), source.getUserId(),
                source.getDomesticId(), source.getProviderType(), Role.USER, source.getGender(), source.getAgeRange());
    }
}