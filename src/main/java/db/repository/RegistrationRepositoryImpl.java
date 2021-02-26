package db.repository;

import domain.RegistrationRepository;
import domain.entity.auth.Session;
import lombok.AllArgsConstructor;
import db.SessionRepositoryJpa;
import db.UserRepositoryJpa;
import db.dto.SessionDb;
import db.dto.UserDb;
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
