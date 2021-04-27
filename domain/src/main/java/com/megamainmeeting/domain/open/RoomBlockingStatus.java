package com.megamainmeeting.domain.open;

import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class RoomBlockingStatus {

    private long id;
    private boolean isBlocked;
    private Set<OpenRequest> openRequests = new LinkedHashSet<>();

    public void add(OpenRequest value){
        openRequests.add(value);
    }

    public List<UserOpens> getByUserId(long userId){
        List<UserOpens> userOpens = new ArrayList<>();
        openRequests.stream().map(it -> it.getByUserId(userId)).forEach(userOpens::addAll);
        return  userOpens;
    }

    public static RoomBlockingStatus getInstance(Room room){
        RoomBlockingStatus roomBlockingStatus = new RoomBlockingStatus();
        roomBlockingStatus.setId(room.getId());
        roomBlockingStatus.setBlocked(room.isBlocked());
        roomBlockingStatus.setOpenRequests(room.getOpenRequests());
        return roomBlockingStatus;
    }
}
