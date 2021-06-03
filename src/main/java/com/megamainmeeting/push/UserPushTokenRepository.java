package com.megamainmeeting.push;

import com.megamainmeeting.domain.error.BadDataException;
import com.megamainmeeting.domain.error.UserNotFoundException;

import java.util.List;

public interface UserPushTokenRepository {



    public void addToken(long userId, String token) throws UserNotFoundException, BadDataException;

    List<String> getToken(long userId) throws UserPushTokenNotFound;
}
