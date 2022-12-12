package com.nyanggle.nyangmail.oauth.jwt;

import com.nyanggle.nyangmail.oauth.dto.UserPrincipal;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuthTokenConverter {
    private final JwtProvider jwt;
    public UserToken fromToken(String t) {
        Claims claims = jwt.getClaims(t);
        String userId = claims.get("userId", String.class);
        String displayName = claims.get("nick", String.class);
        String role = claims.get("role", String.class);
        return new UserToken(userId, displayName, role);
    }

    public String principalToToken(UserPrincipal principal) {
        return jwt.generateToken(principal);
    }
    public void validate(String token) {
        jwt.getClaims(token);
    }
}
