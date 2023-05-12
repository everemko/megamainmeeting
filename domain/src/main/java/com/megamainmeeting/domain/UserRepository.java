package com.megamainmeeting.domain;

import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.domain.registration.NewUser;
import com.megamainmeeting.entity.user.User;

import java.util.NoSuchElementException;

public interface UserRepository {

    // All fields are required
    void saveUser(User user);

    User getById(long id) throws UserNotFoundException;

    boolean isExist(long userId);

    /**
     Return userId;
     */
    long register(NewUser user);
}
