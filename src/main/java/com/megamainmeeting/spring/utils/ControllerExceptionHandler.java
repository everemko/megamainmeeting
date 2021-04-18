package com.megamainmeeting.spring.utils;

import com.megamainmeeting.domain.error.*;
import com.megamainmeeting.domain.error.ErrorMessages;
import com.megamainmeeting.error.TokenNotFoundException;
import com.megamainmeeting.spring.base.FailureResponse;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @Autowired
    Logger logger;

    @ExceptionHandler(Exception.class)
    public final FailureResponse handleAllExceptions(Exception ex, WebRequest request) {
        if(ex instanceof BaseException ){
            return new FailureResponse(ex.getMessage());
        }
        else {
            logger.error(ex.toString());
            return new FailureResponse(ErrorMessages.INTERNAL_SERVER_ERROR);
        }
    }

}
