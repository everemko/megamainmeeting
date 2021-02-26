package domain;

import domain.entity.chat.Room;
import domain.entity.user.User;
import domain.interactor.UserChatCandidateInteractor;

public interface UserMatchNotifier {

    void notifyRoomReady(Room room);

    void notifyUsersRefuse(UserChatCandidateInteractor.RoomPreparing preparing);

    void notifyMatch(UserChatCandidateInteractor.RoomPreparing preparing);
}
