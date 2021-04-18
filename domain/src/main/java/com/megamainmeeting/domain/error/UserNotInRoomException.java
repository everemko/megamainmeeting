package com.megamainmeeting.domain.error;

public class UserNotInRoomException extends BaseException {

    @Override
    public String getMessage() {
        return ErrorMessages.USER_NOT_IN_ROOM;
    }
}
