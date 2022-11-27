package com.nyanggle.nyangmail.exception.code;

public class AlreadyDeletedFishBread extends NyangException{
    public AlreadyDeletedFishBread() {
        super(ErrorCode.FISHBREAD_IS_ALREADY_DELETED);
    }
}
