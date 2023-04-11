package com.megamainmeeting.push;

import com.megamainmeeting.domain.error.BadDataException;
import com.megamainmeeting.domain.error.UserNotFoundException;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface UserPushTokenRepository {

    public void addToken(long userId, String token) throws UserNotFoundException, BadDataException;

    List<String> getTokens(long userId) throws UserPushTokenNotFound;

    Collection<String> getTokens(Set<Long> users);
}
