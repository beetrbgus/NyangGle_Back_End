package com.nyanggle.nyangmail.exception.handler;

import com.nyanggle.nyangmail.exception.ErrorCode;

public class NyangException extends RuntimeException {
    private ErrorCode errorCode;

    public NyangException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public NyangException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public NyangException(String message) {
        super(message);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
