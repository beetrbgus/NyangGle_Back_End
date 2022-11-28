package com.nyanggle.nyangmail.config;

import com.nyanggle.nyangmail.common.RandomIdUtil;
import com.nyanggle.nyangmail.config.filter.TokenAuthFilter;
import com.nyanggle.nyangmail.oauth.jwt.AuthTokenConverter;
import com.nyanggle.nyangmail.oauth.jwt.JwtProvider;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;

import java.nio.charset.StandardCharsets;

@Configuration
public class ApplicationConfig {

    @Value("${nyangletter.auth.header.scheme}")
    private String TOKEN_SCHEME;
    @Value("${nyangletter.jwt.secret}")
    private String secretKey;
    @Value("${nyangletter.jwt.expiryMills}")
    private long expireMills;

    @Bean
    public RandomIdUtil randomIdUtil() {
        return new RandomIdUtil();
    }
    @Bean
    public DefaultOAuth2UserService defaultOAuth2UserService() {
        return new DefaultOAuth2UserService();
    }

    @Bean
    public JwtBuilder jwtBuilder() {
        return Jwts.builder().signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)));
    }

    @Bean
    public JwtParser jwtParser() {
        return Jwts.parserBuilder().setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8)).build();
    }

    @Bean
    public JwtProvider jwtProvider(JwtParser parser, JwtBuilder builder) {
        return new JwtProvider(expireMills, builder, parser);
    }

    @Bean
    public AuthTokenConverter authTokenConverter(JwtProvider jwtProvider) {
        return new AuthTokenConverter(jwtProvider);
    }

    @Bean
    public TokenAuthFilter tokenAuthFilter(AuthTokenConverter authTokenConverter) {
        return new TokenAuthFilter(TOKEN_SCHEME, authTokenConverter);
    }
}