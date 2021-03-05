package com.megamainmeeting.domain;

import com.megamainmeeting.entity.chat.Room;
import com.megamainmeeting.domain.interactor.UserChatCandidateInteractor;
import com.megamainmeeting.entity.chat.RoomPreparing;

public interface UserMatchNotifier {

    void notifyRoomReady(Room room);

    void notifyUsersRefuse(RoomPreparing preparing);

    void notifyMatch(RoomPreparing preparing);
}
