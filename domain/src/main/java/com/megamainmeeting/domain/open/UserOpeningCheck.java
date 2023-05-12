package com.megamainmeeting.domain.open;

import com.megamainmeeting.domain.error.*;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserOpeningCheck {


    private final UserOpensRepository userOpensRepository;
    private final RoomBlockingNotifier roomBlockingNotifier;

    public void checkBeforeMessage(long roomId, long userId) throws RoomNotFoundException,
            RoomIsBlockedException, UserNotInRoomException, OpenRequestNotFoundException {
        Room room = userOpensRepository.getRoom(roomId);
        if (room.isBlocked()) {
            throw new RoomIsBlockedException();
        }
        if(room.isNeedToBeBlocked()){
            OpenRequest openRequest = userOpensRepository.blockRoom(roomId);
            roomBlockingNotifier.notifyUserShouldOpens(room.getUser(userId), openRequest);
            throw new RoomIsBlockedException();
        }
    }

    public void addUserOpen(UserOpens userOpens) throws RoomNotFoundException,
            UserNotInRoomException, UserNotFoundException, OpenRequestNotFoundException {
        userOpensRepository.updateUserOpens(userOpens);
        Room room = userOpensRepository.getRoomByOpenRequestId(userOpens.getOpenRequestId());
        roomBlockingNotifier.notifyRoomOpens(room);
    }

}
