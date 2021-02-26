package db.repository;

import domain.UserChatCandidateQueue;
import domain.entity.user.User;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserChatCandidateQueueImpl implements UserChatCandidateQueue {

    private final Set<User> users = new HashSet<>();

    @Override
    public void add(User user) {
        users.add(user);
    }

    @Override
    public User getMatch(User user) {
        return users.stream().filter(user1 -> true).findFirst().orElse(null);
    }
}
