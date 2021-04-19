package com.megamainmeeting.domain.error;

public abstract class BaseException extends Exception {

    private String message;

    public BaseException(){

    }

    public BaseException(String message){
        this.message = message;
    }

    public String getMessage(){
        if(message == null) return ErrorMessages.INTERNAL_SERVER_ERROR;
        return message;
    }
}
