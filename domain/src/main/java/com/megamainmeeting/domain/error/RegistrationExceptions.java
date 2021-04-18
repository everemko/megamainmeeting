package com.megamainmeeting.domain.error;

public class RegistrationExceptions extends BaseException {

    private final String message;

    public RegistrationExceptions(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
