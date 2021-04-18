package com.megamainmeeting.domain;

import com.megamainmeeting.domain.error.UserAlreadyCandidateException;
import com.megamainmeeting.entity.user.User;

import java.util.List;

public interface UserChatCandidateQueue {

    void add(User user);

    //return userId matched
    User findMatch(long userId);

    boolean isExist(User user);

    void remove(long userId);

    List<Long> getQueue();
}
