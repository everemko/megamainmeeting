package com.megamainmeeting.db.repository;

import com.megamainmeeting.db.SessionRepositoryJpa;
import com.megamainmeeting.db.dto.SessionDb;
import com.megamainmeeting.domain.SessionRepository;
import com.megamainmeeting.domain.error.SessionNotFoundException;
import com.megamainmeeting.entity.auth.Session;
import com.megamainmeeting.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class SessionRepositoryImpl implements SessionRepository {

    @Autowired
    SessionRepositoryJpa sessionRepositoryJpa;

    @Override
    public void save(Session session) {

    }

    @Override
    public User getUser(String token) throws SessionNotFoundException {
        try {
            SessionDb sessionDb = sessionRepositoryJpa.findByToken(token);
            if (sessionDb == null) throw new SessionNotFoundException();
            return sessionDb.getUser().toDomain();
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
