package com.megamainmeeting.dto;

import lombok.Data;

@Data
public class AuthenticationSocketDto {

    private long userId;
    private String token;
}
