package com.nyanggle.nyangmail.config;

import com.nyanggle.nyangmail.oauth.CustomOAuth2UserService;
import com.nyanggle.nyangmail.oauth.OAuth2AuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final CustomOAuth2UserService oAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http //.addFilterAfter()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //url 관련 권한 설정
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/fishbread/**").permitAll()
                .antMatchers("/api/fishbread/**").permitAll()
                .antMatchers("/mycat/**").hasRole("MEMBER");

        http.formLogin().disable()
                .csrf().disable()
                .exceptionHandling()
//                .authenticationEntryPoint() //인증 문제 발생시의 EndPoint
                .and()
                .oauth2Login()
                    .userInfoEndpoint()
                        .userService(oAuth2UserService)
                .and()
                .successHandler(oAuth2AuthenticationSuccessHandler)
                //인증 후 처리
//                .and()
//                .successHandler() // 인증 완료 후의 성공시 처리 Handler
        ;
        return http.build();
    }

}
