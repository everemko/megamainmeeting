package com.megamainmeeting.entity;

import lombok.Getter;

@Getter
public enum UserRole {

    User("user"), Admin("admin");

    UserRole(String authority){
        this.authority = authority;
    }

    private final String authority;
}
