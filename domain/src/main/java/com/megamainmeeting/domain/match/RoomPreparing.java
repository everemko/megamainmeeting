package com.megamainmeeting.domain.match;

import java.util.*;
import java.util.stream.Collectors;

public class RoomPreparing {

    private Map<ChatCandidate, Boolean> users = new HashMap<>();

    public void addUser(ChatCandidate chatCandidate){
        users.put(chatCandidate, false);
    }

    public void setReady(long user){
        users.keySet()
                .stream()
                .filter(it -> it.getUserId() == user)
                .findFirst()
                .ifPresent(chatCandidate -> users.put(chatCandidate, true));
    }

    public void setNotReady(long user){
        users.keySet()
                .stream()
                .filter(it -> it.getUserId() == user)
                .findFirst()
                .ifPresent(chatCandidate -> users.put(chatCandidate, false));
    }

    public Set<Long> getUsers(){
        return users.keySet()
                .stream()
                .map(ChatCandidate::getUserId)
                .collect(Collectors.toSet());
    }

    public Set<ChatCandidate> getReadyUsers(){
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
                .map(ChatCandidate::getUserId)
                .collect(Collectors.toSet());
    }

    public boolean isAllReady(){
        for(boolean status: users.values()){
            if(!status) return false;
        }
        return true;
    }
}
