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
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebMvcConfigurerAdapter {
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
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/api/fishbread/**").permitAll()
                .antMatchers("/api/oauth/**").permitAll()
                .antMatchers("/mycat/**").hasRole("MEMBER");

        http.formLogin().disable()
                .csrf().disable()
                .exceptionHandling()
                    .accessDeniedHandler(jwtAccessDeniedHandler) // jwt 403
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint) // jwt 401
                .and()
                .oauth2Login()
                //인증 후 처리
//                .and()
//                .successHandler() // 인증 완료 후의 성공시 처리 Handler
        ;
        return http.build();
    }
}
