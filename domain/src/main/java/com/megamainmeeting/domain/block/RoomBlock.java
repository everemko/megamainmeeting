package com.megamainmeeting.domain.block;

import lombok.Data;

import java.util.Arrays;

@Data
public class RoomBlock {

    private long roomId;
    private long userId;
    private RoomBlockReason reason;
    private long[] usersInRoom = new long[0];

    public boolean isRoomBlocked(){
        return reason != null;
    }

    public boolean isUserInRoom(long userId){
        return Arrays.stream(usersInRoom).anyMatch(value -> value == userId);
    }
}
