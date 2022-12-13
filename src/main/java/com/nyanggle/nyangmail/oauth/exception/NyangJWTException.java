package com.nyanggle.nyangmail.oauth.exception;

import com.nyanggle.nyangmail.exception.ErrorCode;
import com.nyanggle.nyangmail.exception.handler.NyangException;

public class NyangJWTException extends NyangException {

    public NyangJWTException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public NyangJWTException(ErrorCode errorCode) {
        super(errorCode);
    }
}
