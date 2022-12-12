package com.nyanggle.nyangmail.oauth.jwt;

import com.nyanggle.nyangmail.exception.ErrorCode;
import com.nyanggle.nyangmail.exception.handler.NyangException;
import com.nyanggle.nyangmail.oauth.dto.UserPrincipal;
import com.nyanggle.nyangmail.oauth.exception.NyangJWTException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
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
        final byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        final Key KEY = Keys.hmacShaKeyFor(apiKeySecretBytes);

        Date now = new Date();
        Date expiry = new Date(now.getTime() + ACCESS_TOKEN_EXPIRE);

        return Jwts.builder()
                .signWith(KEY)
                .setSubject((userPrincipal.getDisplayName()))
                .setClaims(createClaims(userPrincipal))
                .setIssuedAt(now)
                .setExpiration(expiry)
                .compact();
    }

    public Map<String,Object> createClaims(UserPrincipal userPrincipal) {
        Map<String,Object> claims = new HashMap<>();
        claims.put("userId", userPrincipal.getUserId());
        claims.put("nick", userPrincipal.getDisplayName());
        claims.put("role", userPrincipal.getAuthorities().stream().findFirst().get().getAuthority());
        return claims;
    }

    public Claims getClaims(String token) {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(apiKeySecretBytes)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SecurityException e) {
            throw new NyangJWTException(ErrorCode.INVALID_JWT_SIGNATURE);
        } catch (MalformedJwtException e) {
            // 유효하지 않은 구성의 토큰
            throw new NyangJWTException(ErrorCode.MALFORMED_JWT_EXCEPTION);
        } catch (ExpiredJwtException e) {
            throw new NyangJWTException(ErrorCode.EXPIRED_JWT_TOKEN);
        } catch (UnsupportedJwtException e) {
            throw new NyangJWTException(ErrorCode.UNSUPPORTED_JWT_TOKEN);
        } catch (IllegalArgumentException e) {
            // 잘못된 JWT
            throw new NyangJWTException(ErrorCode.INVALID_JWT_TOKEN);
        } catch (Exception e) {
            throw new NyangException(e.getMessage());
        }
    }
}
