package com.nyanggle.nyangmail.exception.user;

import com.nyanggle.nyangmail.exception.ErrorCode;
import com.nyanggle.nyangmail.exception.handler.NyangException;

public class OAuthLoginException extends NyangException {
    public OAuthLoginException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public OAuthLoginException(ErrorCode errorCode, String message) {
        super(message, errorCode);
    }
    public OAuthLoginException() {
        super(ErrorCode.KAKAO_LOGIN_IS_FAIL);
    }
}
