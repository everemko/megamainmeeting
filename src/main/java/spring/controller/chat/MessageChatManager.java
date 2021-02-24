package spring.controller.chat;

import domain.entity.chat.Message;

public interface MessageChatManager {

    void sendMessage(Message message);
}
