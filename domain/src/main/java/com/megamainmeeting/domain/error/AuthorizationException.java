package com.megamainmeeting.domain.error;

public class AuthorizationException extends BaseException {

    @Override
    public String getMessage() {
        return ErrorMessages.AUTHORIZATION_ERROR;
    }
}
