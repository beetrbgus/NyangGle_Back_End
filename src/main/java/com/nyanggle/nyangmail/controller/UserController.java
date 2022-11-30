package com.nyanggle.nyangmail.controller;

import com.nyanggle.nyangmail.config.AuthUser;
import com.nyanggle.nyangmail.oauth.CustomOAuth2UserService;
import com.nyanggle.nyangmail.oauth.UserPrincipal;
import com.nyanggle.nyangmail.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PatchMapping("/")
    public ResponseEntity modifyNickname(@AuthUser @AuthenticationPrincipal UserPrincipal userPrincipal,
                                         @RequestParam @Length(min = 2, max = 20) String nickName){
        userService.modifyNickname(userPrincipal.getUserId(),nickName);
        return ResponseEntity.ok().build();
    }
}