package domain;

import com.sun.istack.Nullable;
import domain.entity.user.User;
import domain.error.NotFoundException;

import java.util.NoSuchElementException;

public interface UserChatCandidateQueue {

    void add(User user);


    @Nullable
    User getMatch(User user);
}
