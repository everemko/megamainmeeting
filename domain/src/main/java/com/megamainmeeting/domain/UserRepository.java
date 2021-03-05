package com.megamainmeeting.domain;

import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.entity.user.User;

import java.util.NoSuchElementException;

public interface UserRepository {

    void saveUser(User user);

    User get(long id) throws UserNotFoundException;

    boolean isExist(long userId);
}
