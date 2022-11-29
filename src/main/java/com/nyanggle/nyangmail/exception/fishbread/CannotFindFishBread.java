package com.nyanggle.nyangmail.exception.fishbread;

import com.nyanggle.nyangmail.exception.ErrorCode;
import com.nyanggle.nyangmail.exception.handler.NyangException;

public class CannotFindFishBread extends NyangException {
    public CannotFindFishBread() {
        super(ErrorCode.NOT_FOUND_FISH);
    }
}
