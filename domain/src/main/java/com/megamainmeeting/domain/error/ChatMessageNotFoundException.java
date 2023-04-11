package com.megamainmeeting.domain.error;

public class ChatMessageNotFoundException extends BaseException {

    @Override
    public String getMessage() {
        return ErrorMessages.CHAT_MESSAGE_NOT_FOUND_ERROR;
    }
}
