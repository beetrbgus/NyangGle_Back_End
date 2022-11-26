package com.nyanggle.nyangmail.interfaces.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.FieldError;

import java.util.List;

public class FailureRes extends BasicResponse{
    private String errorCode;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ValidationError> errors;

    public FailureRes(int statusCode, String errorCode, String message) {
        super(statusCode);
        this.errorCode = errorCode;
        this.message = message;
    }

    public FailureRes(List<ValidationError> errors, int statusCode, String errorCode, String message) {
        super(statusCode);
        this.errorCode = errorCode;
        this.message = message;
        this.errors = errors;
    }

    @Getter
    @RequiredArgsConstructor
    public static class ValidationError {

        private final String field;
        private final String message;

        public static ValidationError of(final FieldError fieldError) {
            return new ValidationError(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }
}