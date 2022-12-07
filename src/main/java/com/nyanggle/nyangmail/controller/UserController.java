package com.nyanggle.nyangmail.controller;

import com.nyanggle.nyangmail.config.AuthUser;
import com.nyanggle.nyangmail.exception.ErrorCode;
import com.nyanggle.nyangmail.exception.handler.NyangException;
import com.nyanggle.nyangmail.oauth.jwt.UserToken;
import com.nyanggle.nyangmail.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PatchMapping
    public ResponseEntity modifyNickname(@AuthUser @AuthenticationPrincipal UserToken userToken,
                                         @RequestBody Map<String,String> nicknameMap){
        if(!StringUtils.hasText(nicknameMap.get("nickname"))) {
            throw new NyangException(ErrorCode.INVALID_INPUT_VALUE);
        }
        userService.modifyNickname(userToken.getUserId(), nicknameMap.get("nickname"));
        return ResponseEntity.ok().build();
    }
}