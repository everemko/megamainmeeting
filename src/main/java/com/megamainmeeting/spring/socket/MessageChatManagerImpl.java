package com.megamainmeeting.spring.socket;

import com.megamainmeeting.domain.MessageChatManager;
import com.megamainmeeting.entity.chat.ChatMessage;
import com.megamainmeeting.entity.room.Room;
import com.megamainmeeting.spring.UserSocketClientManager;
import com.megamainmeeting.spring.base.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageChatManagerImpl implements MessageChatManager {

    @Autowired
    RpcFactory rpcFactory;
    @Autowired
    private UserSocketClientManager userSocketManager;

    @Override
    public void sendIgnoreSender(ChatMessage message) {
        BaseRpc response = rpcFactory.getNotification(RpcMethods.NEW_MESSAGE_NOTIFICATION, message);
        Room room = message.getRoom();
        for (long userId : room.getUsers()) {
            if (userId == message.getUserId()) continue;
            userSocketManager.send(userId, response);
        }
    }
}
