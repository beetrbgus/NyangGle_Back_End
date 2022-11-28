package com.nyanggle.nyangmail.oauth.jwt;

import com.nyanggle.nyangmail.oauth.UserPrincipal;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class AuthTokenConverter {
    private final JwtProvider jwt;

    public UserToken fromToken(String t) {
        Claims claims = jwt.claims(t);
        String userId = claims.get("userId", String.class);
        String email = claims.get("email", String.class);
        String displayName = claims.get("displayName", String.class);
        String role = claims.get("role", String.class);
        return new UserToken(userId, email, displayName, role);
    }

    public String toTokenString(Authentication auth) {
        final var principal = (UserPrincipal) auth.getPrincipal();
        Map<String, Object> claims = createClaims(principal);

        return jwt.generateAuthToken(claims);
    }

    public String toTokenString(UserPrincipal principal) {
        return jwt.generateAuthToken(createClaims(principal));
    }

    private Map<String, Object> createClaims(UserPrincipal principal) {
        Map<String, Object> claims = new HashMap<>() {{
            put("userId", principal.getUserId());
            put("email", principal.getEmail());
            put("role", principal.getRoleType());
            put("displayName", principal.getDisplayName());
        }};
        return claims;
    }
}
