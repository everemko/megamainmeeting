package com.megamainmeeting.domain.error;

public class OpenRequestNotFoundException extends BaseException{

    @Override
    public String getMessage() {
        return ErrorMessages.OPEN_REQUEST_NOT_FOUND;
    }
}
