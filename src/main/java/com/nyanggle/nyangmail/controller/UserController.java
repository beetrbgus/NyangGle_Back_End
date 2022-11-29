package com.nyanggle.nyangmail.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nyanggle.nyangmail.oauth.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/oauth")
@RequiredArgsConstructor
public class UserController {
    private final CustomOAuth2UserService oAuth2UserService;

    @PostMapping("/login/{provider}")
    public ResponseEntity userLogin(@PathVariable(name = "provider") String provider, @RequestBody Map<String,String> authCode) throws JsonProcessingException {
        String code = authCode.get("code");
        return ResponseEntity.ok(oAuth2UserService.login(provider,code));
    }
}