package com.megamainmeeting.db.repository;

import com.megamainmeeting.db.UserPushTokenRepositoryJpa;
import com.megamainmeeting.db.UserRepositoryJpa;
import com.megamainmeeting.db.dto.UserDb;
import com.megamainmeeting.db.dto.UserPushTokenDb;
import com.megamainmeeting.domain.error.BadDataException;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.push.UserPushTokenNotFound;
import com.megamainmeeting.push.UserPushTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserPushTokenRepositoryImpl implements UserPushTokenRepository {

    @Autowired
    UserPushTokenRepositoryJpa userPushTokenRepositoryJpa;
    @Autowired
    UserRepositoryJpa userRepository;

    @Override
    public void addToken(long userId, String token) throws UserNotFoundException, BadDataException{
        if(token == null) throw new BadDataException();
        UserDb userDb = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        UserPushTokenDb.getInstance(userDb, token);
    }

    @Override
    public String getToken(long userId) throws UserPushTokenNotFound {
        UserPushTokenDb userPushToken = userPushTokenRepositoryJpa.findById(userId).orElseThrow(UserPushTokenNotFound::new);
        return userPushToken.getToken();
    }
}
