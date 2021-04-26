package com.megamainmeeting.domain.open;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoomBlockingStatus {

    private long id;
    private boolean isBlocked;
    private List<UserOpensSet> opens = new ArrayList<>();

    public void add(UserOpensSet userOpensSet){
        opens.add(userOpensSet);
    }

    public static RoomBlockingStatus getInstance(Room room){
        RoomBlockingStatus roomBlockingStatus = new RoomBlockingStatus();
        roomBlockingStatus.setId(room.getId());
        roomBlockingStatus.setBlocked(room.isBlocked());
        return roomBlockingStatus;
    }
}
