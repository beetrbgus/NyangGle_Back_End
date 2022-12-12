package com.nyanggle.nyangmail.user.persistence;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {
    USER("ROLE_USER", "유저"),
    GUEST("ROLE_GUEST", "방문자");

    private final String key;
    private final String korWord;
}
