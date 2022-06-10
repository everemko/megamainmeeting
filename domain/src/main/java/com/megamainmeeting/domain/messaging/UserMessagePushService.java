package com.megamainmeeting.domain.messaging;

import com.megamainmeeting.entity.chat.ChatMessage;
import com.megamainmeeting.entity.user.User;

import java.util.Set;

public interface UserMessagePushService {

    void sendMessage(ChatMessage message, User sender);
}
