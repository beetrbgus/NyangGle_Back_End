package com.nyanggle.nyangmail.config;

import com.nyanggle.nyangmail.common.RandomIdUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;

@Configuration
public class ApplicationConfig {
    @Bean
    public RandomIdUtil randomIdUtil() {
        return new RandomIdUtil();
    }
    @Bean
    public DefaultOAuth2UserService defaultOAuth2UserService() {
        return new DefaultOAuth2UserService();
    }
}