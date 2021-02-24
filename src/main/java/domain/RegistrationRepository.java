package domain;

import domain.entity.auth.Session;

public interface RegistrationRepository {

    Session registerAnon();
}
