package com.nyanggle.nyangmail.config;

import com.nyanggle.nyangmail.oauth.CustomAuthArgumentResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {

    @Value("${nyangletter.frontEnd.cloudFlare}")
    private String CLOUDFLARE_PAGES;

    @Value("${nyangletter.frontEnd.aws}")
    private String AWS;

    @Value("${nyangletter.frontEnd.local}")
    private String LOCALHOST;

    @Value("${nyangletter.auth.header.scheme}")
    private String TOKEN_SCHEME;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(LOCALHOST, AWS, CLOUDFLARE_PAGES)
                .allowedMethods("*")
                .allowCredentials(true)
                .allowedHeaders("Accept", "Content-Type",
                        "Referer","User-Agent",
                        TOKEN_SCHEME)
                .maxAge(3600);
    }

    @Bean
    public PageableHandlerMethodArgumentResolverCustomizer customizer() {
        return pageableResolver -> {
            pageableResolver.setOneIndexedParameters(true);
            pageableResolver.setMaxPageSize(45);
        };
    }
    @Bean
    public CustomAuthArgumentResolver argumentResolver() {
        return new CustomAuthArgumentResolver();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(argumentResolver());
    }
}
