package com.megamainmeeting.entity.chat;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class RoomPreparing {

    @Getter
    private long user1 = -1;
    @Getter
    private long user2 = -1;
    private boolean user1Ready = false;
    private boolean user2Ready = false;

    public RoomPreparing(long user1, long user2){
        this.user1 = user1;
        this.user2 = user2;
    }

    public void setReady(long user){
        if(user == user1) user1Ready = true;
        if(user == user2) user2Ready = true;
    }

    public Set<Long> getUsers(){
        return new HashSet<>(Arrays.asList(user1, user2));
    }

    public boolean isAllReady(){
        return user1Ready && user2Ready;
    }
}
