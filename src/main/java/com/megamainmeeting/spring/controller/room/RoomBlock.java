package com.megamainmeeting.spring.controller.room;

import com.megamainmeeting.domain.block.RoomBlockReason;
import com.megamainmeeting.domain.error.BadDataException;
import lombok.Data;

@Data
public class RoomBlock {

    private long userId = -1;
    private long roomId = -1;
    private RoomBlockReason reason;

    public void checkValid() throws Exception{
        if(userId == -1) throw new BadDataException();
        if(roomId == -1) throw new BadDataException();
        if(reason == null) throw new BadDataException();
    }
}
