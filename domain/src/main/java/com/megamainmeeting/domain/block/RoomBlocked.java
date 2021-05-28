package com.megamainmeeting.domain.block;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomBlocked {

    private long roomId;
    private long userId;
    private RoomBlockReason reason;

    public static RoomBlocked getInstance(long roomId, long userId, RoomBlockReason reason){
        RoomBlocked roomBlockedChatNotification = new RoomBlocked();
        roomBlockedChatNotification.setRoomId(roomId);
        roomBlockedChatNotification.setReason(reason);
        roomBlockedChatNotification.setUserId(userId);
        return roomBlockedChatNotification;
    }
}
