package spring.db.repository;

import domain.RegistrationRepository;
import domain.UserRepository;
import domain.entity.auth.Session;
import lombok.AllArgsConstructor;
import spring.db.SessionRepositoryJpa;
import spring.db.UserRepositoryJpa;
import spring.db.dto.SessionDto;
import spring.db.dto.UserDto;

@AllArgsConstructor
public class RegistrationRepositoryImpl implements RegistrationRepository {

    private UserRepositoryJpa userRepository;
    private SessionRepositoryJpa sessionRepository;

    @Override
    public Session registerAnon() {
        UserDto userDto = new UserDto();
        userRepository.save(userDto);
        SessionDto sessionDto = new SessionDto();
        sessionRepository.save(sessionDto);
        return sessionDto.toDomain();
    }
}
