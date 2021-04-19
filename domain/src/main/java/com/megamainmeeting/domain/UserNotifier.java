package com.megamainmeeting.domain;

import com.megamainmeeting.entity.chat.ChatMessage;
import com.megamainmeeting.entity.room.Room;
import com.megamainmeeting.domain.match.RoomPreparing;

public interface UserNotifier {

    void notifyRoomReady(Room room);

    void notifyUsersRefuse(RoomPreparing preparing);

    void notifyUserRefuseChat(long userId);

    void notifyMatch(RoomPreparing preparing);

    void notifyChatMessageUpdated(ChatMessage message);
}
