package domain;

import domain.entity.auth.Session;

public interface SessionRepository {

    void save(Session session);

    Session get(long userId);
}
