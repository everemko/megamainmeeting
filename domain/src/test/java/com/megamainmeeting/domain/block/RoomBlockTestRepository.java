package com.megamainmeeting.domain.block;

import com.megamainmeeting.domain.error.RoomNotFoundException;
import lombok.Data;

import static com.megamainmeeting.domain.block.RoomBlockInteractorTestContext.*;

@Data
public class RoomBlockTestRepository implements RoomBlockRepository {

    private boolean isRoomBlocked = false;

    @Override
    public RoomBlock findByRoomId(long id) throws RoomNotFoundException {
        if (id != EXIST_ROOM) throw new RoomNotFoundException();
        RoomBlock roomBlock = new RoomBlock();
        roomBlock.setRoomId(id);
        roomBlock.setUsersInRoom(USERS_IN_ROOM);
        if (isRoomBlocked) {
            roomBlock.setUserId(EXIST_USER);
            roomBlock.setReason(RoomBlockReason.Other);
        }
        return roomBlock;
    }

    @Override
    public void blockRoom(NewRoomBlock room) {

    }
}
