package com.megamainmeeting.auth;

public interface AuthenticationUserRepository {

    boolean isExist(long userId);
}
