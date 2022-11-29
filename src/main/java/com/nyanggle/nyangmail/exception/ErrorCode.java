package com.nyanggle.nyangmail.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    SYSTEM_EXCEPTION(500, "S000", "Internal Server Error"),
    NOT_FOUND_HANDLER(404, "S404", "404 NOT FOUND"),

    INVALID_INPUT_VALUE(400, "B001", "Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "B002", "Method not allowed"),
    HANDLE_ACCESS_DENIED(403, "B006", "Access is Denied"),
    INTERNAL_SERVER_ERROR(500, "C004", "Server Error"),
    INVALID_TYPE_VALUE(400, "C005", "Invalid Type Value"),

    NOT_FOUND_USER(404, "U001", "No such User"),
    NOT_FOUND_FISH(404, "F001", "No such FishBread"),
    FISHBREAD_IS_ALREADY_DELETED(404, "F001", "Already deleted"),
    UNAUTHORIZED(401, "U002", "Not Authorized on user"),
    JWT_DECODE_FAILURE(500, "J001", "JWT cannot be decoded"),
    JWT_ENCODE_FAILURE(500, "J002", "DTO encode failure"),
    PROVIDER_IS_UNCORRECT(401,"U003","provider is not kakao")
    ;
    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
