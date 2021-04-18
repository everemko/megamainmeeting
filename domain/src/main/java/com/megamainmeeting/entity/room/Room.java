package com.megamainmeeting.entity.room;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class Room {

    private long id;
    private Set<Long> users = new HashSet<>();
    private LocalDateTime createdAt;

    public void addUser(long userId){
        users.add(userId);
    }

    public boolean isUserInRoom(long userId){
        return users.contains(userId);
    }
}

