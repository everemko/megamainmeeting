package com.megamainmeeting.domain.error;

public class BadDataException extends BaseException {

    @Override
    public String getMessage() {
        return "Bad data";
    }
}
