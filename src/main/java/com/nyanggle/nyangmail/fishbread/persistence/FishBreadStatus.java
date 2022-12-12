package com.nyanggle.nyangmail.fishbread.persistence;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum FishBreadStatus {
    TOTAL("total", "전체"),
    READ("read", "읽음"),
    UNREAD("unread", "안읽음"),
    DELETED("delete", "삭제됨");

    private final String key;
    private final String korWord;
}
