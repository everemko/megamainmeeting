package com.megamainmeeting.domain.error;

public class RoomIsBlockedException extends BaseException {

    @Override
    public String getMessage() {
        return ErrorMessages.ROOM_IS_BLOCKED;
    }
}
