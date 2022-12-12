package com.nyanggle.nyangmail.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class LoginRes {
    private String uUid;
    private String token;
    private String nickname;
}