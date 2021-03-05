package com.megamainmeeting.domain;

import com.megamainmeeting.entity.auth.Session;

public interface RegistrationRepository {

    Session registerAnon();
}
