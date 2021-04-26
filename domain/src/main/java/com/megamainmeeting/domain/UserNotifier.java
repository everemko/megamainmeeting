package com.megamainmeeting.domain;

import com.megamainmeeting.domain.open.RoomBlockingStatus;
import com.megamainmeeting.domain.open.UserOpensSet;
import com.megamainmeeting.entity.chat.ChatMessage;
import com.megamainmeeting.entity.room.Room;
import com.megamainmeeting.domain.match.RoomPreparing;

public interface UserNotifier {

    void notifyRoomReady(Room room);

    void notifyUsersRefuse(RoomPreparing preparing);

    void notifyUserRefuseChat(long userId);

    void notifyMatch(RoomPreparing preparing);

    void notifyChatMessageUpdated(ChatMessage message);

    void notifyUserShouldOpens(long userId, UserOpensSet userOpensSet);

    void notifyUserOpens(long userId, RoomBlockingStatus roomBlockingStatus);
}
