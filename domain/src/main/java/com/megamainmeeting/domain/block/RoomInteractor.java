package com.megamainmeeting.domain.block;

import com.megamainmeeting.domain.UserNotifier;
import com.megamainmeeting.domain.error.*;

import javax.inject.Inject;

public class RoomInteractor {

    @Inject
    private RoomBlockRepository roomBlockRepository;
    @Inject
    private RoomDeleteRepository roomDeleteRepository;
    @Inject
    private UserNotifier userNotifier;

    public void removeRoom(long userId, long roomId) throws RoomNotFoundException, UserNotInRoomException, UserNotFoundException, BadDataException {
        if (userId == -1) throw new BadDataException();
        if (roomId == -1) throw new BadDataException();
        RoomDeleted roomDelete = roomDeleteRepository.findByRoomId(roomId);
        if (!roomDelete.isUserInRoom(userId)) throw new UserNotInRoomException();
        roomDeleteRepository.deleteRoom(roomId, userId);
    }

    public void blockRoom(
            NewRoomBlock roomBlock
    ) throws RoomNotFoundException, RoomIsBlockedException, UserNotInRoomException, BadDataException {
        roomBlock.checkValid();
        RoomBlock room = roomBlockRepository.findByRoomId(roomBlock.getRoomId());
        if (!room.isUserInRoom(roomBlock.getUserId())) throw new UserNotInRoomException();
        if (room.isRoomBlocked()) throw new RoomIsBlockedException();
        for (Long user : room.getUsersInRoom()) {
            userNotifier.notifyRoomBlocked(user, roomBlock);
        }
    }
}
