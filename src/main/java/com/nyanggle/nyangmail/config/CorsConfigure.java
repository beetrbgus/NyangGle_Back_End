package com.nyanggle.nyangmail.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfigure implements WebMvcConfigurer {
    /**
     * Todo 프론트 배포시 allowedOrigin 추가
     * Todo 토큰 적용시 expose Header 추가
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOriginPatterns("*")
                .maxAge(3600);
        WebMvcConfigurer.super.addCorsMappings(registry);
    }
}
