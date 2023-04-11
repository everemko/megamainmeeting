package com.megamainmeeting.auth;

import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.entity.Authentication;

public interface AuthenticationInteractor {

    public boolean isAuth(Authentication auth) throws UserNotFoundException;
}
