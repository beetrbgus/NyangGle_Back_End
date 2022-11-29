package com.nyanggle.nyangmail.exception.handler;

import com.nyanggle.nyangmail.exception.ErrorCode;
import com.nyanggle.nyangmail.interfaces.dto.response.FailureRes;
import com.nyanggle.nyangmail.interfaces.dto.response.FailureRes.ValidationError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NyangException.class)
    public ResponseEntity<Object> globalExceptionHandler(NyangException e) {
        return handleExceptionInternal(e);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, ErrorCode.INVALID_INPUT_VALUE);
    }

    private ResponseEntity<Object> handleExceptionInternal(BindException e, ErrorCode errorCode) {
        List<ValidationError> validError = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ValidationError::of)
                .collect(Collectors.toList());
        return ResponseEntity.status(errorCode.getStatus())
                .body(buildResponse(errorCode, errorCode.getMessage(), validError));
    }
    private ResponseEntity<Object> handleExceptionInternal(NyangException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getStatus())
                .body(buildResponse(errorCode));
    }
    private FailureRes buildResponse(ErrorCode errorCode) {
        return new FailureRes(errorCode.getStatus(), errorCode.getCode(), errorCode.getMessage());
    }

    private FailureRes buildResponse(ErrorCode errorCode, String message) {
        return new FailureRes(null, errorCode.getStatus(), errorCode.getCode(), message);
    }

    private FailureRes buildResponse(ErrorCode errorCode, String message, List<ValidationError> ve) {
        return new FailureRes(ve, errorCode.getStatus(), errorCode.getCode(), message);
    }
}
