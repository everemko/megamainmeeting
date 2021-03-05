package com.megamainmeeting.domain;

import com.megamainmeeting.entity.chat.ChatMessage;

public interface MessageChatManager {

    void sendIgnoreSender(ChatMessage message);
}
