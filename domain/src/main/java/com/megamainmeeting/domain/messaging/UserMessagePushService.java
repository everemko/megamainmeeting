package com.megamainmeeting.domain.messaging;

import com.megamainmeeting.entity.chat.ChatMessage;
import com.megamainmeeting.entity.user.User;

import java.util.Set;

public interface UserMessagePushService {

    void sendImage(Set<Long> users, String imageUrl, String senderName);

    void sendMessageWithImage(Set<Long> users, String message, String imageUrl, String senderName);

    void sendMessage(Set<Long> users, String message, String senderName);

    void sendMessage(ChatMessage message, User sender);

    void sendOpeningRequest(Set<Long> users, String senderName);
}
