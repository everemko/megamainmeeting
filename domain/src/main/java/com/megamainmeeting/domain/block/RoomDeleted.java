package com.megamainmeeting.domain.block;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class RoomDeleted {

    private long roomId;
    private long userId;
    private boolean isRoomDeleted;
    private Set<Long> usersInRoom;

    public boolean isUserInRoom(long userId){
        return usersInRoom.contains(userId);
    }
}
