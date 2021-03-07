package com.megamainmeeting.db.repository;

import com.megamainmeeting.domain.UserChatCandidateQueue;
import com.megamainmeeting.domain.error.UserAlreadyCandidateException;
import com.megamainmeeting.entity.user.User;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

public class UserChatCandidateQueueImpl implements UserChatCandidateQueue {

    private final Set<User> users = new HashSet<>();

    @Override
    public void add(User user){
        users.add(user);
    }

    @Override
    public User findMatch(User user) {
        return users.stream().filter(user1 -> true).findFirst().orElse(null);
    }

    @Override
    public boolean isExist(User user) {
        return users.contains(user);
    }

    @Override
    public void remove(long userId) {
        User user = new User();
        user.setId(userId);
        users.remove(user);
    }
}
