package com.nyanggle.nyangmail.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nyanggle.nyangmail.oauth.CustomOAuth2UserService;
import com.nyanggle.nyangmail.oauth.UserPrincipal;
import com.nyanggle.nyangmail.oauth.jwt.UserToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/api/oauth")
@RequiredArgsConstructor
public class AuthController {
    private final CustomOAuth2UserService oAuth2UserService;

    @PostMapping("/login/{provider}")
    public ResponseEntity userLogin(@PathVariable(name = "provider") String provider, @RequestBody Map<String,String> authCode) throws JsonProcessingException {
        String code = authCode.get("code");
        return ResponseEntity.ok(oAuth2UserService.login(provider,code));
    }
    @PostMapping("/logout/{provider}")
    public void userLogOut(HttpServletRequest request, HttpServletResponse response, @PathVariable(name = "provider") String provider, @AuthenticationPrincipal UserToken userToken) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        oAuth2UserService.logout(provider,userToken);
        new SecurityContextLogoutHandler().logout(request, response, authentication);
    }
}