package com.nyanggle.nyangmail.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nyanggle.nyangmail.common.RandomIdUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.web.client.RestTemplate;

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

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}