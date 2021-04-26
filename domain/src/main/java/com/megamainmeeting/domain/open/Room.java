package com.megamainmeeting.domain.open;

import com.megamainmeeting.domain.error.UserNotInRoomException;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Data
public class Room {

    public static final long MAX_MESSAGES_PER_LEVEL = 3;

    private long id;
    private long messageCount;
    private Set<User> users;

    public boolean isBlocked(){
        long level = messageCount / MAX_MESSAGES_PER_LEVEL;
        boolean isBlocked = false;
        for(User user: users){
            if(!user.isOpen(level)) isBlocked = true;
        }
        return isBlocked;
    }

    public User getUser(long userId) throws UserNotInRoomException {
        return users.stream()
                .filter(it -> it.getId() == userId)
                .findFirst()
                .orElseThrow(UserNotInRoomException::new);
    }

    public void addUserOpens(long userId, UserOpenType type) throws UserNotInRoomException{
        Optional<User> optionalUser = users.stream().filter(it -> it.getId() == userId).findFirst();
        if(optionalUser.isEmpty()) throw new UserNotInRoomException();
        User user = optionalUser.get();
        user.addOpens(type);
    }

    public void checkIsUserInRoom(long userId) throws UserNotInRoomException{
        if(users.stream().noneMatch(it -> it.getId() == userId)) throw new UserNotInRoomException();
    }
}
