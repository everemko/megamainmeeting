package com.megamainmeeting.error;

import com.megamainmeeting.domain.error.BaseException;
import com.megamainmeeting.domain.error.ErrorMessages;

public class TokenNotFoundException extends BaseException {

    @Override
    public String getMessage() {
        return ErrorMessages.TOKEN_NOT_FOUND_ERROR;
    }
}
