package com.nyanggle.nyangmail.oauth;

import com.nyanggle.nyangmail.persistence.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class UserPrincipal implements OAuth2User, UserDetails {
    private final String userId;
    private final String email;
    private final String displayName;
    private final String roleType;
    private final String domesticId;
    private final String providerType;
    private final Collection<GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return displayName;
    }

    public static UserPrincipal create(User user) {
        return new UserPrincipal(
                user.getUserUid(),
                user.getEmail(),
                user.getDisplayName(),
                user.getRole().getKey(),
                user.getDomesticId(),
                user.getProviderType(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getKey())),
                null
        );
    }
    public static UserPrincipal create(User user, Map<String,Object> attributes) {
        UserPrincipal userPrincipal = create(user);
        userPrincipal.setAttributes(attributes);

        return userPrincipal;
    }

}
