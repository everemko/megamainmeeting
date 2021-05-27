package com.megamainmeeting.push;

import com.megamainmeeting.domain.error.BadDataException;
import com.megamainmeeting.domain.error.UserNotFoundException;

public interface UserPushTokenRepository {



    public void addToken(long userId, String token) throws UserNotFoundException, BadDataException;

    String getToken(long userId) throws UserPushTokenNotFound;
}
