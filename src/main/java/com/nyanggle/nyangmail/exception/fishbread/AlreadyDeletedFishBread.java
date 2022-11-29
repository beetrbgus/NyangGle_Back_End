package com.nyanggle.nyangmail.exception.fishbread;

import com.nyanggle.nyangmail.exception.ErrorCode;
import com.nyanggle.nyangmail.exception.handler.NyangException;

public class AlreadyDeletedFishBread extends NyangException {
    public AlreadyDeletedFishBread() {
        super(ErrorCode.FISHBREAD_IS_ALREADY_DELETED);
    }
}
