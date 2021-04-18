package com.megamainmeeting.domain.error;

public class RoomNotFoundException extends BaseException {

    @Override
    public String getMessage() {
        return ErrorMessages.ROOM_NOT_FOUND;
    }
}
