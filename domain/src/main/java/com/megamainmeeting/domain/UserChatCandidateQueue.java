package com.megamainmeeting.domain;

import com.megamainmeeting.domain.error.UserAlreadyCandidateException;
import com.megamainmeeting.domain.match.ChatCandidate;
import com.megamainmeeting.entity.user.User;

import java.util.List;

public interface UserChatCandidateQueue {

    void add(ChatCandidate chatCandidate);

    //return userId matched
    ChatCandidate findMatch(ChatCandidate chatCandidate);

    boolean isExist(long userid);

    void remove(long userId);

    List<Long> getQueue();
}
