package com.megamainmeeting.database.repository;

import com.megamainmeeting.database.UserPushTokenRepositoryJpa;
import com.megamainmeeting.database.UserRepositoryJpa;
import com.megamainmeeting.domain.error.BadDataException;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.database.dto.UserDb;
import com.megamainmeeting.database.dto.UserPushTokenDb;
import com.megamainmeeting.push.UserPushTokenNotFound;
import com.megamainmeeting.push.UserPushTokenRepository;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
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
        UserPushTokenDb saved = userPushTokenRepositoryJpa.getByToken(token);
        if (saved != null) return;
        UserDb userDb = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        UserPushTokenDb userPushTokenDb = UserPushTokenDb.getInstance(userDb, token);
        userPushTokenRepositoryJpa.save(userPushTokenDb);
    }

    @Override
    public List<String> getTokens(long userId) {
        List<UserPushTokenDb> userPushToken = userPushTokenRepositoryJpa.getByUserId(userId);
        return userPushToken.stream().map(UserPushTokenDb::getToken).collect(Collectors.toList());
    }

    @Override
    public Collection<String> getTokens(Set<Long> users) {
        return users.stream()
                .map(this::getTokens)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }
}
