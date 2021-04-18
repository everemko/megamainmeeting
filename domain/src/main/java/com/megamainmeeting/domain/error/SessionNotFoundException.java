package com.megamainmeeting.domain.error;

public class SessionNotFoundException extends BaseException {

    @Override
    public String getMessage() {
        return ErrorMessages.SESSION_NOT_FOUND_ERROR;
    }
}
