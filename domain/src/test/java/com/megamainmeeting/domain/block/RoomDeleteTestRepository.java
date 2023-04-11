package com.megamainmeeting.domain.block;

import com.megamainmeeting.domain.error.RoomNotFoundException;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

import static com.megamainmeeting.domain.block.RoomBlockInteractorTestContext.*;

@Data
public class RoomDeleteTestRepository implements RoomDeleteRepository {

    private boolean isRoomDeleted = false;

    @Override
    public RoomDeleted findByRoomId(long roomId) throws RoomNotFoundException {
        if(roomId != EXIST_ROOM) throw new RoomNotFoundException();
        RoomDeleted roomDelete = new RoomDeleted();
        roomDelete.setRoomDeleted(isRoomDeleted);
        roomDelete.setUsersInRoom(Arrays.stream(USERS_IN_ROOM).boxed().collect(Collectors.toSet()));
        roomDelete.setUserId(EXIST_USER);
        roomDelete.setRoomId(roomId);
        return roomDelete;
    }

    @Override
    public void deleteRoom(long roomId, long userId) {

    }
}
