package com.megamainmeeting.database.repository;

import com.megamainmeeting.database.SessionRepositoryJpa;
import com.megamainmeeting.database.UserRepositoryJpa;
import com.megamainmeeting.database.mapper.UserDbMapper;
import com.megamainmeeting.domain.RegistrationRepository;
import com.megamainmeeting.domain.registration.NewUser;
import com.megamainmeeting.database.dto.SessionDb;
import com.megamainmeeting.database.dto.UserDb;
import com.megamainmeeting.domain.registration.RegistrationTokenResult;
import com.megamainmeeting.entity.auth.Session;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Component
public class RegistrationRepositoryImpl implements RegistrationRepository {

    @Inject
    UserDbMapper userDbMapper;
    @Inject
    private UserRepositoryJpa userRepository;
    @Inject
    private SessionRepositoryJpa sessionRepository;

    @Override
    @Transactional()
    public String registerAnon(NewUser user) {
        UserDb userDb = new UserDb();
        userDb.setDateBirth(user.getDateBirth());
        userDb.setGender(user.getGender());
        userDb.setGenderMatch(user.getGenderMatch());
        userDb.setName(user.getName());
        userRepository.save(userDb);
        SessionDb sessionDb = SessionDb.getInstance(userDb);
        sessionRepository.save(sessionDb);
    }

    private Session map(SessionDb sessionDb){
        return new Session(
                sessionDb.getId(),
                sessionDb.getToken(),
                userDbMapper.mapToUser(sessionDb.getUser())
        );
    }
}
