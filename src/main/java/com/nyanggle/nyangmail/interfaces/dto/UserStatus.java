package com.nyanggle.nyangmail.interfaces.dto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserStatus {
    NORMAL("normal","정상"),
    ABNORMAL("abNormal","비정상"),
    DELETED;

    private String key;
    private String koWord;

    UserStatus(String key, String koWord) {
        this.key = key;
        this.koWord = koWord;
    }
}
