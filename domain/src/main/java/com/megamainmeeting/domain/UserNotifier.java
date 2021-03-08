package com.megamainmeeting.domain;

import com.megamainmeeting.entity.chat.ChatMessage;
import com.megamainmeeting.entity.chat.Room;
import com.megamainmeeting.domain.interactor.UserChatCandidateInteractor;
import com.megamainmeeting.entity.chat.RoomPreparing;

public interface UserNotifier {

    void notifyRoomReady(Room room);

    void notifyUsersRefuse(RoomPreparing preparing);

    void notifyUserRefuseChat(long userId);

    void notifyMatch(RoomPreparing preparing);

    void notifyChatMessageUpdated(ChatMessage message);
}
