package com.megamainmeeting.domain.block;

import com.megamainmeeting.domain.error.RoomNotFoundException;
import com.megamainmeeting.domain.error.UserNotFoundException;

public interface RoomDeleteRepository {

    public RoomDeleted findByRoomId(long roomId) throws RoomNotFoundException;

    public void deleteRoom(long roomId, long userId) throws RoomNotFoundException, UserNotFoundException;
}
