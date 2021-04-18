package com.megamainmeeting.interactor;

import com.megamainmeeting.domain.*;
import com.megamainmeeting.domain.error.UserAlreadyCandidateException;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.domain.error.UserNotMatchException;
import com.megamainmeeting.entity.user.User;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserChatCandidateInteractor {



    private final UserChatCandidateQueue userMatchQueue;
    private final UserRepository userRepository;

    private final UserChatMatcher userChatMatcher;
    private final UserChatPreparer userChatPreparer;

    public void add(long userId) throws UserNotFoundException, UserAlreadyCandidateException{
        User user = userRepository.get(userId);
        if(userMatchQueue.isExist(user)) throw new UserAlreadyCandidateException();
        userChatMatcher.match(user);
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
