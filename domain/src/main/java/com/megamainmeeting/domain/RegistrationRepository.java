package com.megamainmeeting.domain;

import com.megamainmeeting.domain.registration.NewAnonumousUser;
import com.megamainmeeting.entity.auth.Session;

public interface RegistrationRepository {

    Session registerAnon(NewAnonumousUser user);
}
