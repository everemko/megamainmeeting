package com.megamainmeeting.domain.error;

public class RoomBlockedException extends BaseException {

    @Override
    public String getMessage() {
        return "Room is blocked";
    }
}
