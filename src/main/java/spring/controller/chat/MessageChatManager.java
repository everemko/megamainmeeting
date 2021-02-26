package spring.controller.chat;

import domain.entity.chat.ChatMessage;

public interface MessageChatManager {

    void sendMessage(ChatMessage message);

    void sendIgnoreSender(ChatMessage message);
}
