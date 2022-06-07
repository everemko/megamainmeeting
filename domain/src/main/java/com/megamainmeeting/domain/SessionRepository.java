package com.megamainmeeting.domain;

import com.megamainmeeting.domain.error.SessionNotFoundException;
import com.megamainmeeting.entity.auth.Session;
import com.megamainmeeting.entity.user.User;

import java.util.Set;

public interface SessionRepository {

    void save(Session session);

    User getUser(String token) throws SessionNotFoundException;

    long getUserId(String token) throws SessionNotFoundException;

    Set<String> getTokens(long userId);
}
