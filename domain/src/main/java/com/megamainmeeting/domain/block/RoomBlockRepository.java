package com.megamainmeeting.domain.block;

import com.megamainmeeting.domain.block.entity.NewRoomBlock;
import com.megamainmeeting.domain.block.entity.RoomBlock;
import com.megamainmeeting.domain.error.RoomNotFoundException;

public interface RoomBlockRepository {

    public RoomBlock findByRoomId(long id) throws RoomNotFoundException;

    public void blockRoom(NewRoomBlock room);
}
