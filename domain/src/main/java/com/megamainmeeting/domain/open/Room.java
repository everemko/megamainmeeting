package com.megamainmeeting.domain.open;

import com.megamainmeeting.domain.error.UserNotInRoomException;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class Room {

    public static final long MAX_MESSAGES_PER_LEVEL = 3;

    private long id;
    private long messageCount;
    private Set<OpenRequest> openRequests;
    private Set<User> users;

    public boolean isBlocked(){
        if(isAllOpens()) return false;
        long level = messageCount / MAX_MESSAGES_PER_LEVEL;
        if(openRequests.size() != level) return false;
        for(OpenRequest openRequest: openRequests){
            if(!openRequest.isAllOpens(users.size())) return true;
        }
        return false;
    }

    public boolean isNeedToBeBlocked(){
        if(isAllOpens()) return false;
        long level = messageCount / MAX_MESSAGES_PER_LEVEL;
        return level > openRequests.size();
    }

    private boolean isAllOpens(){
        return openRequests.size() == OpenRequestType.values().length;
    }

    public User getUser(long userId) throws UserNotInRoomException {
        return users.stream()
                .filter(it -> it.getId() == userId)
                .findFirst()
                .orElseThrow(UserNotInRoomException::new);
    }

    public void checkIsUserInRoom(long userId) throws UserNotInRoomException{
        if(users.stream().noneMatch(it -> it.getId() == userId)) throw new UserNotInRoomException();
    }

    public static Room getInstance(long roomId, long messageCount, Set<User> users, Set<OpenRequest> openRequests){
        Room room = new Room();
        room.setId(roomId);
        room.setMessageCount(messageCount);
        room.setUsers(users);
        room.setOpenRequests(openRequests);
        return room;
    }

    public long[] getUsers(){
        return users.stream().mapToLong(User::getId).toArray();
    }

    public void incrementMessageCount(){
        messageCount++;
    }
}
