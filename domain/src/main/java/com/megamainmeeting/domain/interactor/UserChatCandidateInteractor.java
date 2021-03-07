package com.megamainmeeting.domain.interactor;

import com.megamainmeeting.domain.*;
import com.megamainmeeting.domain.error.UserAlreadyCandidateException;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.entity.chat.Room;
import com.megamainmeeting.entity.chat.RoomPreparing;
import com.megamainmeeting.entity.user.User;
import com.megamainmeeting.domain.error.UserNotMatchException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserChatCandidateInteractor {

    private final UserChatCandidateQueue userMatchQueue;
    private final RoomRepository roomRepository;
    private final UserNotifier matchNotifier;
    private final UserRepository userRepository;
    private final RoomPreparingRepository roomPreparingRepository;

    public void add(long userId) throws UserNotFoundException, UserAlreadyCandidateException {
        User user = userRepository.get(userId);
        if(userMatchQueue.isExist(user)) throw new UserAlreadyCandidateException();
        User userMatched = userMatchQueue.findMatch(user);
        if (userMatched == null) {
            userMatchQueue.add(user);
        } else {
            RoomPreparing roomPreparing = new RoomPreparing(user.getId(), userMatched.getId());
            roomPreparingRepository.add(roomPreparing);
            userMatchQueue.remove(userMatched.getId());
            matchNotifier.notifyMatch(roomPreparing);
        }
    }

    public void setUserReady(long userId) throws UserNotMatchException{
        RoomPreparing roomPreparing = roomPreparingRepository.get(userId);
        roomPreparing.setReady(userId);
        if(roomPreparing.isAllReady()){
            Room room = roomRepository.create(roomPreparing.getUser1(), roomPreparing.getUser2());
            matchNotifier.notifyRoomReady(room);
        }
    }

    public void setUserNotReady(long userId) throws UserNotMatchException {
        RoomPreparing roomPreparing = roomPreparingRepository.get(userId);
        matchNotifier.notifyUsersRefuse(roomPreparing);
        roomPreparing.getUsers().forEach(roomPreparingRepository::remove);
    }

    public void remove(long userId) throws UserNotFoundException{
        if(!userRepository.isExist(userId)) throw new UserNotFoundException();
        userMatchQueue.remove(userId);
    }

}
