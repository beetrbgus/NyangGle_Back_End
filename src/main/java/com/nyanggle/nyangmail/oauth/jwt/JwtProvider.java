package com.nyanggle.nyangmail.oauth.jwt;

import com.nyanggle.nyangmail.oauth.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtProvider {
    @Value("${nyangletter.jwt.accesstoken.expiryMills}")
    private long ACCESS_TOKEN_EXPIRE;
    @Value("${nyangletter.jwt.secret}")
    private String secretKey;

    public String generateToken(UserPrincipal userPrincipal) {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        Key KEY = Keys.hmacShaKeyFor(apiKeySecretBytes);

        Date now = new Date();
        Date expiry = new Date(now.getTime() + ACCESS_TOKEN_EXPIRE);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(createClaims(userPrincipal))
                .setExpiration(expiry)
                .signWith(KEY)
                .compact();
    }

    public Map<String,Object> createClaims(UserPrincipal userPrincipal) {
        Map<String,Object> claims = new HashMap<>();
        claims.put("sub", userPrincipal.getUserId());
        claims.put("nick", userPrincipal.getDisplayName());
        claims.put("role", userPrincipal.getAuthorities().stream().findFirst().get().getAuthority());
        return claims;
    }

    public Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }catch (SecurityException e) {
            throw new JwtException("Invalid JWT signature");
        } catch (MalformedJwtException e) {
            // 유효하지 않은 구성의 토큰
            throw new JwtException("Invalid JWT token.");
        } catch (ExpiredJwtException e) {
            throw new JwtException("Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            throw new JwtException("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            // 잘못된 JWT
            throw new JwtException("JWT token compact of handler are invalid.");
        }
    }

    public JwtCode validate(String token) {
        try {
            //jwtParser.parseClaimsJws(token);
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

    public enum JwtCode {
        DENIED,
        ACCESS,
        EXPIRED;
    }
}
