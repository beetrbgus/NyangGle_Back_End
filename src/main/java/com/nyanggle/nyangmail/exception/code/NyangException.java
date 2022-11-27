package com.nyanggle.nyangmail.exception.code;

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

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
