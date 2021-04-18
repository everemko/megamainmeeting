package com.megamainmeeting.domain.error;

public class UserAlreadyCandidateException extends BaseException{

    @Override
    public String getMessage() {
        return ErrorMessages.USER_ALREADY_CANDIDATE;
    }
}
