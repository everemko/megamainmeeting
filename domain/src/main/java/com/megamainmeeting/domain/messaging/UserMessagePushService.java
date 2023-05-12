package com.megamainmeeting.domain.messaging;

import com.megamainmeeting.domain.messaging.entity.ChatMessage;
import com.megamainmeeting.entity.user.User;

public interface UserMessagePushService {

    void sendMessage(ChatMessage message, User sender);
}
