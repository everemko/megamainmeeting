package com.megamainmeeting.entity;

import lombok.Data;
import lombok.NonNull;

@Data
public class Authentication {

    private long userId;
    private String token;
}
