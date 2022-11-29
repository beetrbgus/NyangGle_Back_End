package com.nyanggle.nyangmail.config;

import com.nyanggle.nyangmail.oauth.CustomAuthArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * Todo 프론트 배포시 allowedOrigin 추가
     * Todo 토큰 적용시 expose Header 추가
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173", "https://nyangnyang-letter.pages.dev")
                .allowedMethods("*")
                .allowCredentials(true)
                .exposedHeaders("X-NYANG-AUTH-TOKEN")
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
