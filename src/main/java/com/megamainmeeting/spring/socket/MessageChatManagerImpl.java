package com.megamainmeeting.spring.socket;

import com.megamainmeeting.domain.MessageChatManager;
import com.megamainmeeting.domain.messaging.entity.ChatMessage;
import com.megamainmeeting.domain.messaging.entity.NewChatMessage;
import com.megamainmeeting.entity.room.Room;
import com.megamainmeeting.spring.socket.dto.BaseRpc;
import com.megamainmeeting.spring.socket.dto.RpcFactory;
import com.megamainmeeting.spring.socket.dto.RpcMethods;
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

    }

    @Override
    public void sendIgnoreSender(NewChatMessage message) {
        BaseRpc response = rpcFactory.getNotification(RpcMethods.NEW_MESSAGE_NOTIFICATION, message);
        Room room = message.getRoom();
        for (long userId : room.getUsers()) {
            if (userId == message.getUserId()) continue;
            userSocketManager.send(userId, response);
        }
    }
}
