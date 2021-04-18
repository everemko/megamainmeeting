package com.megamainmeeting.entity.room;

import com.megamainmeeting.entity.user.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class RoomList {

    private List<Room> list = new ArrayList<Room>();

    public RoomList(List<Room> list) {
        this.list.addAll(list);
    }

    public boolean isUserInRoom(long userId) {
        return list.stream().anyMatch(room ->
            room.isUserInRoom(userId)
        );
    }

    public boolean isEmpty(){
        return list.isEmpty();
    }
}
