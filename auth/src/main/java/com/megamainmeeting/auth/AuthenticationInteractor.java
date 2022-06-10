package com.megamainmeeting.auth;

import com.megamainmeeting.domain.SessionRepository;
import com.megamainmeeting.domain.UserRepository;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.entity.auth.Authentication;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import javax.inject.Inject;
import java.util.Set;

@AllArgsConstructor(access = AccessLevel.PUBLIC, onConstructor = @__({ @Inject }))
public class AuthenticationInteractor {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    public boolean isAuth(Authentication auth) throws UserNotFoundException {
        if (userRepository.isExist(auth.getUserId())) throw new UserNotFoundException();
        Set<String> tokens = sessionRepository.getTokens(auth.getUserId());
        return tokens.contains(auth.getToken());
    }
}
