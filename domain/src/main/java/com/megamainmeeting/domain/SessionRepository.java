package com.megamainmeeting.domain;

import com.megamainmeeting.domain.error.SessionNotFoundException;
import com.megamainmeeting.entity.auth.Session;
import com.megamainmeeting.entity.user.User;

public interface SessionRepository {

    void save(Session session);

    User getUser(String token) throws SessionNotFoundException;
}
