package com.megamainmeeting.domain.match;

import com.megamainmeeting.domain.UserChatCandidateQueue;
import com.megamainmeeting.domain.error.AddChatCandidateException;
import com.megamainmeeting.domain.error.UserAlreadyCandidateException;
import com.megamainmeeting.entity.user.User;
import lombok.AllArgsConstructor;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class UserChatMatcher {

    @Inject
    private UserChatCandidateQueue userMatchQueue;
    @Inject
    private UserChatPreparer userChatPreparer;

    public void match(ChatCandidate chatCandidate) throws AddChatCandidateException, UserAlreadyCandidateException {
        if(userMatchQueue.isExist(chatCandidate.getUserId())) throw new UserAlreadyCandidateException();
        chatCandidate.checkValid();
        ChatCandidate match = userMatchQueue.findMatch(chatCandidate);
        if (match == null) {
            userMatchQueue.add(chatCandidate);
        } else {
            Set<ChatCandidate> set = new HashSet<>(Arrays.asList(chatCandidate, match));
            userChatPreparer.prepare(set);
        }
    }
}

