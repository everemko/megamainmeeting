package com.megamainmeeting.auth;

import java.util.Set;

public interface AuthenticationSessionsRepository {

    Set<String> getTokens(long userId);
}
