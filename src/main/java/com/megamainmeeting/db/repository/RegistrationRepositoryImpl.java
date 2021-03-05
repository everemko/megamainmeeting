package com.megamainmeeting.db.repository;

import com.megamainmeeting.domain.RegistrationRepository;
import com.megamainmeeting.entity.auth.Session;
import lombok.AllArgsConstructor;
import com.megamainmeeting.db.SessionRepositoryJpa;
import com.megamainmeeting.db.UserRepositoryJpa;
import com.megamainmeeting.db.dto.SessionDb;
import com.megamainmeeting.db.dto.UserDb;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
public class RegistrationRepositoryImpl implements RegistrationRepository {

    private UserRepositoryJpa userRepository;
    private SessionRepositoryJpa sessionRepository;

    @Override
    @Transactional()
    public Session registerAnon() {
        UserDb userDb = new UserDb();
        userRepository.save(userDb);
        SessionDb sessionDb = SessionDb.getInstance(userDb);
        sessionRepository.save(sessionDb);
        return sessionDb.toDomain();
    }
}
