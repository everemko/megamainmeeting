package com.megamainmeeting.interactor;

import com.megamainmeeting.domain.UserChatCandidateQueue;
import com.megamainmeeting.entity.user.User;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class UserChatMatcher {

    private final UserChatCandidateQueue userMatchQueue;
    private final UserChatPreparer userChatPreparer;

    public void match(User user){
        User userMatched = userMatchQueue.findMatch(user.getId());
        if (userMatched == null) {
            userMatchQueue.add(user);
        } else {
            Set<User> set = new HashSet<>(Arrays.asList(user, userMatched));
            userChatPreparer.prepare(set);
        }
    }
}

