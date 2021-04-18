package com.megamainmeeting.spring.utils;

import com.megamainmeeting.domain.error.*;
import com.megamainmeeting.error.ErrorMessages;
import com.megamainmeeting.error.TokenNotFoundException;
import com.megamainmeeting.spring.base.FailureResponse;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @Autowired
    Logger logger;

    @ExceptionHandler(Exception.class)
    public final FailureResponse handleAllExceptions(Exception ex, WebRequest request) {
        if (ex instanceof UserNotFoundException) {
            return new FailureResponse(ErrorMessages.USER_NOT_FOUND);
        } else if (ex instanceof UserAlreadyCandidateException) {
            return new FailureResponse(ErrorMessages.USER_ALREADY_CANDIDATE);
        } else if (ex instanceof RoomNotFoundException) {
            return new FailureResponse(ErrorMessages.ROOM_NOT_FOUND);
        } else if( ex instanceof SessionNotFoundException){
            return new FailureResponse(ErrorMessages.SESSION_NOT_FOUND_ERROR);
        } else if( ex instanceof TokenNotFoundException){
            return new FailureResponse(ErrorMessages.TOKEN_NOT_FOUND_ERROR);
        } else if( ex instanceof UserNotInRoomException){
            return new FailureResponse(ErrorMessages.USER_NOT_IN_ROOM);
        }

        else {
            logger.error(ex.toString());
            return new FailureResponse(ErrorMessages.INTERNAL_SERVER_ERROR);
        }
    }

}
