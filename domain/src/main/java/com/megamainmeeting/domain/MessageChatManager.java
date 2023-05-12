package com.megamainmeeting.domain;

import com.megamainmeeting.domain.messaging.entity.ChatMessage;

public interface MessageChatManager {

    void sendIgnoreSender(ChatMessage message);
}
