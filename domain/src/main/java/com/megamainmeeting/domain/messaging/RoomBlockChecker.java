package com.megamainmeeting.domain.messaging;

public interface RoomBlockChecker {

    boolean isRoomBlockedForUser(long roomId, long userId);

    boolean isRoomNeedToBlockForUser(long roomId, long userid);
}
