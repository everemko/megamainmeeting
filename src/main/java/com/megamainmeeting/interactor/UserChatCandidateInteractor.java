package com.megamainmeeting.interactor;

import com.megamainmeeting.db.RoomRepositoryJpa;
import com.megamainmeeting.domain.*;
import com.megamainmeeting.domain.error.AddChatCandidateException;
import com.megamainmeeting.domain.match.UserChatMatcher;
import com.megamainmeeting.domain.match.UserChatPreparer;
import com.megamainmeeting.domain.error.UserAlreadyCandidateException;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.domain.error.UserNotMatchException;
import com.megamainmeeting.domain.match.ChatCandidate;
import com.megamainmeeting.entity.room.RoomList;
import com.megamainmeeting.entity.user.User;
import com.megamainmeeting.spring.controller.chat.ChatCandidateRequest;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserChatCandidateInteractor {



    private final UserChatCandidateQueue userMatchQueue;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final UserChatMatcher userChatMatcher;
    private final UserChatPreparer userChatPreparer;

    public void add(long userId, ChatCandidateRequest chatCandidateRequest) throws UserNotFoundException, UserAlreadyCandidateException, AddChatCandidateException {
        User user = userRepository.get(userId);
        RoomList roomList = roomRepository.getList(userId);
        ChatCandidate chatCandidate = new ChatCandidate();
        chatCandidate.setAgeFrom(chatCandidateRequest.getAgeFrom());
        chatCandidate.setAgeTo(chatCandidateRequest.getAgeTo());
        chatCandidate.setChatGoal(chatCandidateRequest.getChatGoal());
        chatCandidate.setUserId(userId);
        chatCandidate.setAge(user.getAge());
        chatCandidate.setRoomList(roomList);
        userChatMatcher.match(chatCandidate);
    }

    public void setUserReady(long userId) throws UserNotMatchException, UserNotFoundException{
        User user = userRepository.get(userId);
        userChatPreparer.setReady(user);
    }

    public void setUserNotReady(long userId) throws UserNotMatchException, UserNotFoundException {
        User user = userRepository.get(userId);
        userChatPreparer.setNotReady(user);
    }

    public void remove(long userId) throws UserNotFoundException{
        if(!userRepository.isExist(userId)) throw new UserNotFoundException();
        userMatchQueue.remove(userId);
    }

}
