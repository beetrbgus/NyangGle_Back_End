package com.nyanggle.nyangmail.config;

import com.nyanggle.nyangmail.oauth.jwt.JwtAccessDeniedHandler;
import com.nyanggle.nyangmail.oauth.jwt.JwtAuthenticationEntryPoint;
import com.nyanggle.nyangmail.oauth.filter.JwtExceptionFilter;
import com.nyanggle.nyangmail.oauth.filter.TokenAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtExceptionFilter jwtExceptionFilter;
    private final TokenAuthFilter tokenAuthFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.addFilterBefore(tokenAuthFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtExceptionFilter, tokenAuthFilter.getClass());
        http.sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //url 관련 권한 설정
        http.cors().and().authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/api/fishbread/**").permitAll()
                .antMatchers("/api/oauth/**").permitAll()
                .antMatchers("/mycat/**").hasRole("MEMBER");

        http.formLogin().disable()
                .csrf().disable()
                .exceptionHandling()
                    .accessDeniedHandler(jwtAccessDeniedHandler) // jwt 403
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint) // jwt 401
        ;
        return http.build();
    }
}
