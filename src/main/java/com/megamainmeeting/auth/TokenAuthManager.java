package com.megamainmeeting.auth;

import com.megamainmeeting.domain.SessionRepository;
import com.megamainmeeting.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import static com.megamainmeeting.domain.error.ErrorMessages.SESSION_NOT_FOUND_ERROR;

public class TokenAuthManager implements AuthenticationManager {

    @Autowired
    private SessionRepository sessionRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        long userId = Long.parseLong((String) authentication.getPrincipal());
        String token = (String) authentication.getCredentials();

        Set<String> tokens = sessionRepository.getTokens(userId);
        if (tokens.contains(token)) {
            Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(UserRole.User.getAuthority()));
            PreAuthenticatedAuthenticationToken authenticationWithAuthorities = new PreAuthenticatedAuthenticationToken(userId, token, authorities);
            authenticationWithAuthorities.setAuthenticated(true);
            return authenticationWithAuthorities;
        } else {
            throw new TokenAuthenticationException(SESSION_NOT_FOUND_ERROR);
        }
    }
}
