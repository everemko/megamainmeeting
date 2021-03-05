package com.megamainmeeting.domain;

import com.megamainmeeting.domain.error.UserAlreadyCandidateException;
import com.megamainmeeting.entity.user.User;

public interface UserChatCandidateQueue {

    void add(User user);

    User findMatch(User user);

    boolean isExist(User user);

    void remove(long userId);
}
