package com.nyanggle.nyangmail.exception.user;

import com.nyanggle.nyangmail.exception.ErrorCode;
import com.nyanggle.nyangmail.exception.handler.NyangException;

public class CannotFindUser extends NyangException {
    public CannotFindUser() {
        super(ErrorCode.NOT_FOUND_USER);
    }
}
