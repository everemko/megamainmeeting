package com.megamainmeeting.domain.interactor;

import com.megamainmeeting.domain.*;
import com.megamainmeeting.domain.error.UserAlreadyCandidateException;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.entity.chat.Room;
import com.megamainmeeting.entity.chat.RoomPreparing;
import com.megamainmeeting.entity.user.User;
import com.megamainmeeting.domain.error.UserNotChatMatchException;
import lombok.AllArgsConstructor;

import java.util.*;

@AllArgsConstructor
public class UserChatCandidateInteractor {

    private final UserChatCandidateQueue userMatchQueue;
    private final RoomRepository roomRepository;
    private final UserMatchNotifier matchNotifier;
    private final UserRepository userRepository;
    private final RoomPreparingRepository roomPreparingRepository;

    public void add(long userId) throws UserNotFoundException, UserAlreadyCandidateException {
        User user = userRepository.get(userId);
        if(userMatchQueue.isExist(user)) throw new UserAlreadyCandidateException();
        User user2 = userMatchQueue.findMatch(user);
        if (user2 == null) {
            userMatchQueue.add(user);
        } else {
            RoomPreparing roomPreparing = new RoomPreparing(user.getId(), user2.getId());
            roomPreparingRepository.add(roomPreparing);
            matchNotifier.notifyMatch(roomPreparing);
        }
    }

    public void setUserReady(long userId) throws UserNotChatMatchException, UserNotFoundException{
        if(!userRepository.isExist(userId)) throw new UserNotFoundException();
        RoomPreparing roomPreparing = roomPreparingRepository.get(userId);
        if(roomPreparing == null) throw new UserNotChatMatchException();
        roomPreparing.setReady(userId);
        if(roomPreparing.isAllReady()){
            Room room = roomRepository.create(roomPreparing.getUser1(), roomPreparing.getUser2());
            matchNotifier.notifyRoomReady(room);
        }
    }

    public void setUserNotReady(long userId){
        RoomPreparing roomPreparing = roomPreparingRepository.get(userId);
        if(roomPreparing == null) throw new NoSuchElementException();
        matchNotifier.notifyUsersRefuse(roomPreparing);
        roomPreparing.getUsers().forEach(roomPreparingRepository::remove);
    }

    public void remove(long userId) throws UserNotFoundException{
        if(!userRepository.isExist(userId)) throw new UserNotFoundException();
        userMatchQueue.remove(userId);
    }

}
