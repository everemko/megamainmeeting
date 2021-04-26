package com.megamainmeeting.domain.open;

import com.megamainmeeting.domain.error.RoomIsBlockedException;
import com.megamainmeeting.domain.error.RoomNotFoundException;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.domain.error.UserNotInRoomException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserOpeningCheck {


    private final UserOpensRepository userOpensRepository;
    private final RoomBlockingNotifier roomBlockingNotifier;

    public void checkBeforeMessage(long roomId, long userId) throws RoomNotFoundException,
            RoomIsBlockedException, UserNotInRoomException {
        Room room = userOpensRepository.getRoom(roomId);
        if(room.isBlocked()){
            roomBlockingNotifier.notifyUserShouldOpens(room.getUser(userId), roomId);
            throw new RoomIsBlockedException();
        }
    }

    public void checkAfterMessage(long roomId) throws RoomNotFoundException{
        Room room = userOpensRepository.getRoom(roomId);
        if(room.isBlocked()){
            roomBlockingNotifier.notifyRoomShouldOpens(room);
        }
    }

    public void addUserOpen(UserOpens userOpens) throws RoomNotFoundException, UserNotInRoomException, UserNotFoundException {
        userOpensRepository.updateUserOpens(userOpens);
        Room room = userOpensRepository.getRoom(userOpens.getRoomId());
        roomBlockingNotifier.notifyRoomOpens(room);
    }

}
