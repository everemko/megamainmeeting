package com.megamainmeeting.database.repository;

import com.megamainmeeting.database.SessionRepositoryJpa;
import com.megamainmeeting.auth.AuthenticationSessionsRepository;
import com.megamainmeeting.database.mapper.UserDbMapper;
import com.megamainmeeting.domain.SessionRepository;
import com.megamainmeeting.domain.error.SessionNotFoundException;
import com.megamainmeeting.database.dto.SessionDb;
import com.megamainmeeting.entity.auth.Session;
import com.megamainmeeting.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Set;

@Component
public class SessionRepositoryImpl implements SessionRepository, AuthenticationSessionsRepository {

    @Autowired
    SessionRepositoryJpa sessionRepositoryJpa;
    @Inject
    private UserDbMapper userDbMapper;

    @Override
    public void save(Session session) {

    }

    @Override
    public User getUser(String token) throws SessionNotFoundException {
        try {
            SessionDb sessionDb = sessionRepositoryJpa.findByToken(token);
            if (sessionDb == null) throw new SessionNotFoundException();
            return userDbMapper.mapToUser(sessionDb.getUser());
        } catch (Exception e) {
            throw new SessionNotFoundException();
        }
    }

    @Override
    public long getUserId(String token) throws SessionNotFoundException {
        try {
            SessionDb sessionDb = sessionRepositoryJpa.findByToken(token);
            if (sessionDb == null) throw new SessionNotFoundException();
            return sessionDb.getUser().getId();
        } catch (Exception e) {
            throw new SessionNotFoundException();
        }
    }

    @Override
    public Set<String> getTokens(long userId) {
        return sessionRepositoryJpa.getTokens(userId);
    }
}
