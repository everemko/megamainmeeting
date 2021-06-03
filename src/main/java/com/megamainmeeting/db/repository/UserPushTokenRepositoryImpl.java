package com.megamainmeeting.db.repository;

import com.megamainmeeting.db.UserPushTokenRepositoryJpa;
import com.megamainmeeting.db.UserRepositoryJpa;
import com.megamainmeeting.db.dto.UserDb;
import com.megamainmeeting.db.dto.UserPushTokenDb;
import com.megamainmeeting.domain.error.BadDataException;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.push.UserPushTokenNotFound;
import com.megamainmeeting.push.UserPushTokenRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class UserPushTokenRepositoryImpl implements UserPushTokenRepository {

    @Autowired
    UserPushTokenRepositoryJpa userPushTokenRepositoryJpa;
    @Autowired
    UserRepositoryJpa userRepository;
    @Autowired
    Logger logger;

    @Override
    public void addToken(long userId, String token) throws UserNotFoundException, BadDataException {
        if (token == null) throw new BadDataException();
        try {
            UserPushTokenDb saved = userPushTokenRepositoryJpa.getByToken(token);
            if(saved != null) return;
            UserDb userDb = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
            UserPushTokenDb userPushTokenDb = UserPushTokenDb.getInstance(userDb, token);
            userPushTokenRepositoryJpa.save(userPushTokenDb);
        } catch (Throwable exception){
            logger.info(this.getClass().getSimpleName(), exception);
        }
    }

    @Override
    public List<String> getToken(long userId) throws UserPushTokenNotFound {
        List<UserPushTokenDb> userPushToken = userPushTokenRepositoryJpa.getByUserId(userId);
        if (userPushToken == null) throw new UserPushTokenNotFound();
        return userPushToken.stream().map(UserPushTokenDb::getToken).collect(Collectors.toList());
    }
}
