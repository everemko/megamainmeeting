package com.megamainmeeting.auth;

import org.springframework.security.core.AuthenticationException;

class TokenAuthenticationException extends AuthenticationException {

    public TokenAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public TokenAuthenticationException(String msg) {
        super(msg);
    }
}
