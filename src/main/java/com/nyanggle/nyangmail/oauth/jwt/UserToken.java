package com.nyanggle.nyangmail.oauth.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserToken {
    private String userId;
    private String displayName;
    private String role;
}
