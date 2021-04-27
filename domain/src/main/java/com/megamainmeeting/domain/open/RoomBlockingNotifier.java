package com.megamainmeeting.domain.open;


import com.megamainmeeting.domain.UserNotifier;
import com.megamainmeeting.domain.error.UserNotInRoomException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RoomBlockingNotifier {

    private UserNotifier userNotifier;

    public void notifyUserShouldOpens(User user, OpenRequest openRequest) throws UserNotInRoomException {
        userNotifier.notifyUserShouldOpens(user.getId(),  openRequest);
    }

    public void notifyRoomShouldOpens(Room room, OpenRequest openRequest){
        room.getUsers()
                .forEach(it -> {
                    userNotifier.notifyUserShouldOpens(it.getId(), openRequest);
                });
    }

    public void notifyRoomOpens(Room room){
        RoomBlockingStatus roomBlockingStatus = RoomBlockingStatus.getInstance(room);
        room.getOpenRequests()
                .forEach(roomBlockingStatus::add);
        room.getUsers()
                .forEach(it -> {
                    userNotifier.notifyUserOpens(it.getId(), roomBlockingStatus);
                });
    }
}
