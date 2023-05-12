package com.megamainmeeting.domain.registration;

import lombok.Data;

@Data
public class RegistrationTokenResult {

    private final String token;
    private final String userId;
}
