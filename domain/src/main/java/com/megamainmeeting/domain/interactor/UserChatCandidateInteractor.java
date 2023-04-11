package com.megamainmeeting.domain.interactor;

import com.megamainmeeting.domain.RoomRepository;
import com.megamainmeeting.domain.UserChatCandidateQueue;
import com.megamainmeeting.domain.UserRepository;
import com.megamainmeeting.domain.error.AddChatCandidateException;
import com.megamainmeeting.domain.error.UserAlreadyCandidateException;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.domain.error.UserNotMatchException;
import com.megamainmeeting.domain.match.ChatCandidate;
import com.megamainmeeting.domain.match.UserChatMatcher;
import com.megamainmeeting.domain.match.UserChatPreparer;
import com.megamainmeeting.entity.chat.ChatCandidateRequest;
import com.megamainmeeting.entity.room.RoomList;
import com.megamainmeeting.entity.user.User;
import lombok.AllArgsConstructor;

import javax.inject.Inject;

public class UserChatCandidateInteractor {

    @Inject
    UserChatCandidateQueue userMatchQueue;
    @Inject
    UserRepository userRepository;
    @Inject
    RoomRepository roomRepository;
    @Inject
    UserChatMatcher userChatMatcher;
    @Inject
    UserChatPreparer userChatPreparer;

    public void add(long userId, ChatCandidateRequest chatCandidateRequest) throws UserNotFoundException, UserAlreadyCandidateException, AddChatCandidateException {
        User user = userRepository.getById(userId);
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
        User user = userRepository.getById(userId);
        userChatPreparer.setReady(user);
    }

    public void setUserNotReady(long userId) throws UserNotMatchException, UserNotFoundException {
        User user = userRepository.getById(userId);
        userChatPreparer.setNotReady(user);
    }

    public void remove(long userId) throws UserNotFoundException{
        if(!userRepository.isExist(userId)) throw new UserNotFoundException();
        userMatchQueue.remove(userId);
    }

}
