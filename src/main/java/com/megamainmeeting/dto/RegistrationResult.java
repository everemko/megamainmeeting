package com.megamainmeeting.dto;

import lombok.Data;

@Data
public class RegistrationResult {

    private String token;
    private long userId;
}
