package com.megamainmeeting.domain.error;

public class UserNotMatchException extends BaseException{

    @Override
    public String getMessage() {
        return ErrorMessages.USER_NOT_FOUND_MATCH_ERROR;
    }
}
