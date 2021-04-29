package com.megamainmeeting.domain.error;

public class AvatarSizeException extends BaseException {

    @Override
    public String getMessage() {
        return ErrorMessages.AVATAR_SIZE_EXCEPTION;
    }
}
