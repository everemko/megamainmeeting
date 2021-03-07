package com.megamainmeeting.spring.socket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.megamainmeeting.domain.UserNotifier;
import com.megamainmeeting.dto.RoomReadyResult;
import com.megamainmeeting.entity.chat.ChatMessage;
import com.megamainmeeting.entity.chat.Room;
import com.megamainmeeting.entity.chat.RoomPreparing;
import com.megamainmeeting.spring.UserSocketClientManager;
import com.megamainmeeting.spring.base.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMatchNotifierImpl implements UserNotifier {

    @Autowired
    RpcFactory rpcFactory;
    @Autowired
    UserSocketClientManager userSocketManager;

    @Override
    public void notifyRoomReady(Room room) {
        RoomReadyResult result = new RoomReadyResult(room.getId());
        BaseRpc response = rpcFactory.getNotification(RpcMethods.CHAT_ROOM_CREATED_NOTIFICATION, result);
        for (long user : room.getUsers()) {
            userSocketManager.send(user, response);
        }
    }

    @Override
    public void notifyMatch(RoomPreparing preparing) {
        BaseRpc response = rpcFactory.getNotification(RpcMethods.USER_MATCH_FOUND_NOTIFICATION);
        for (long user : preparing.getUsers()) {
            userSocketManager.send(user, response);
        }
    }

    @Override
    public void notifyUsersRefuse(RoomPreparing preparing) {
        BaseRpc response = rpcFactory.getNotification(RpcMethods.USERS_REFUSE_ROOM);
        for (long user : preparing.getUsers()) {
            userSocketManager.send(user, response);
        }
    }

    @Override
    public void notifyChatMessageUpdated(ChatMessage message) {
        BaseRpc response = rpcFactory.getNotification(RpcMethods.MESSAGE_HAS_BEEN_READ_NOTIFICATION, message);
        for(long user: message.getRoom().getUsers()){
            userSocketManager.send(user, response);
        }
    }
}
