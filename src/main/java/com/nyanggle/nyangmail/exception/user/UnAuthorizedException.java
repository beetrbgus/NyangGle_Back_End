package com.nyanggle.nyangmail.exception.user;

import com.nyanggle.nyangmail.exception.ErrorCode;
import com.nyanggle.nyangmail.exception.handler.NyangException;

public class UnAuthorizedException extends NyangException {
    public UnAuthorizedException(String message, ErrorCode errorCode) {
        super(message, ErrorCode.UNAUTHORIZED);
    }

    public UnAuthorizedException() {
        super(ErrorCode.UNAUTHORIZED);
    }
}
