package com.nyanggle.nyangmail.oauth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

@RequiredArgsConstructor
public class JwtProvider {
    @Value("${nyangletter.jwt.expiryMills}")
    private final long expireMilliSecond;
    private final JwtBuilder jwtBuilder;
    private final JwtParser jwtParser;

    public String generateAuthToken(Map<String, Object> claims) {
        var now = new Date();
        var expiry = new Date(now.getTime() + expireMilliSecond);

        return jwtBuilder
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .setSubject((String) claims.get("displayName"))
                .compact();
    }
    public Claims claims(String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public JwtCode validate(String token) {
        try {
            jwtParser.parseClaimsJws(token);
            return JwtCode.ACCESS;
        } catch (ExpiredJwtException ex) {
            return JwtCode.EXPIRED;
        } catch (UnsupportedJwtException ex) {
            return JwtCode.DENIED;
        } catch (IllegalArgumentException ex) {
//      log.error("JWT claims string is empty.");
        }
        return JwtCode.DENIED;
    }
    @Transactional
    public String republishAccessToken(){
//    Authentication authentication =
        return "";
    }
    public enum JwtCode {
        DENIED,
        ACCESS,
        EXPIRED;
    }
}
