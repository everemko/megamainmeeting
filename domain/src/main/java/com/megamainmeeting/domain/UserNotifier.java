package com.megamainmeeting.domain;

import com.megamainmeeting.domain.block.entity.NewRoomBlock;
import com.megamainmeeting.domain.open.OpenRequest;
import com.megamainmeeting.domain.open.RoomBlockingStatus;
import com.megamainmeeting.domain.messaging.entity.ChatMessage;
import com.megamainmeeting.domain.match.RoomPreparing;

public interface UserNotifier {

    void notifyRoomReady(com.megamainmeeting.entity.room.Room room);

    void notifyUsersRefuse(RoomPreparing preparing);

    void notifyUserRefuseChat(long userId);

    void notifyMatch(RoomPreparing preparing);

    void notifyChatMessageUpdated(ChatMessage message);

    void notifyUserShouldOpens(long userId, OpenRequest openRequest);

    void notifyUserOpens(long userId, RoomBlockingStatus roomBlockingStatus);

    void notifyRoomBlocked(long userId, NewRoomBlock roomBlockedChatNotification);
}
