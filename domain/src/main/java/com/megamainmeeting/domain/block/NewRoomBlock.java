package com.megamainmeeting.domain.block;

import com.megamainmeeting.domain.error.BadDataException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor()
@NoArgsConstructor
public class NewRoomBlock {

    private long roomId;
    private long userId;
    private RoomBlockReason reason;

    public static NewRoomBlock getBlockedInstance(long roomId, long userId, RoomBlockReason roomBlockReason) {
        return new NewRoomBlock(roomId, userId, roomBlockReason);
    }

    public void checkValid() throws BadDataException {
        if (userId == -1) throw new BadDataException();
        if (roomId == -1) throw new BadDataException();
        if (reason == null) throw new BadDataException();
    }
}
