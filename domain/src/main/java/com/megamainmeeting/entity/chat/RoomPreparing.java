package com.megamainmeeting.entity.chat;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class RoomPreparing {

    private Map<Long, Boolean> users = new HashMap<>();

    public void addUser(long user){
        users.put(user, false);
    }

    public void setReady(long user){
        users.put(user, true);
    }

    public void setNotReady(long user){
        users.put(user, false);
    }

    public Set<Long> getUsers(){
        return users.keySet();
    }

    public Set<Long> getReadyUsers(){
        return users.entrySet()
                .stream()
                .filter(Map.Entry::getValue)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    public Set<Long> getNotReadyUsers(){
        return users.entrySet()
                .stream()
                .filter(Map.Entry::getValue)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    public boolean isAllReady(){
        for(boolean status: users.values()){
            if(!status) return false;
        }
        return true;
    }
}
