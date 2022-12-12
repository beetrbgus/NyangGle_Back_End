package com.nyanggle.nyangmail.common.dto;

public class BasicResponse {
    int code;
    public BasicResponse(int code) {
        this.code = code;
    }
    public int getCode() {
        return this.code;
    }
    private BasicResponse() {}
}
