package com.megamainmeeting.domain.error;

public class UserNotFoundException extends BaseException {

    @Override
    public String getMessage() {
        return ErrorMessages.USER_NOT_FOUND;
    }
}
