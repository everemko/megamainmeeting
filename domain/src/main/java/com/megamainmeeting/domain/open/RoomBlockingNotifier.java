package com.megamainmeeting.domain.open;


import com.megamainmeeting.domain.UserNotifier;
import com.megamainmeeting.domain.error.UserNotInRoomException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RoomBlockingNotifier {

    private UserNotifier userNotifier;

    public void notifyUserShouldOpens(User user, long roomId) throws UserNotInRoomException {
        UserOpensSet userOpensSet = UserOpensSet.getInstanceAvailable(user, roomId);
        userNotifier.notifyUserShouldOpens(user.getId(),  userOpensSet);
    }

    public void notifyRoomShouldOpens(Room room){
        room.getUsers()
                .forEach(it -> {
                    UserOpensSet userOpensSet = UserOpensSet.getInstanceAvailable(it, room.getId());
                    userNotifier.notifyUserShouldOpens(it.getId(), userOpensSet);
                });
    }

    public void notifyRoomOpens(Room room){
        RoomBlockingStatus roomBlockingStatus = RoomBlockingStatus.getInstance(room);
        room.getUsers()
                .forEach(it -> {
                    UserOpensSet userOpensSet = UserOpensSet.getInstanceUsed(it, room.getId());
                    roomBlockingStatus.add(userOpensSet);
                });
        room.getUsers()
                .forEach(it -> {
                    userNotifier.notifyUserOpens(it.getId(), roomBlockingStatus);
                });
    }
}
