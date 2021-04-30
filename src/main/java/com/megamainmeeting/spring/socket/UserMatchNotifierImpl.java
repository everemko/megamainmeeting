package com.megamainmeeting.spring.socket;

import com.megamainmeeting.db.RoomRepositoryJpa;
import com.megamainmeeting.db.dto.RoomDb;
import com.megamainmeeting.domain.UserNotifier;
import com.megamainmeeting.domain.open.OpenRequest;
import com.megamainmeeting.domain.open.RoomBlockingStatus;
import com.megamainmeeting.domain.open.UserOpensSet;
import com.megamainmeeting.dto.RoomResponse;
import com.megamainmeeting.entity.chat.ChatMessage;
import com.megamainmeeting.entity.room.Room;
import com.megamainmeeting.domain.match.RoomPreparing;
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
    @Autowired
    RoomRepositoryJpa roomRepositoryJpa;

    @Override
    public void notifyRoomReady(Room room) {
        RoomDb roomDb = roomRepositoryJpa.findById(room.getId()).orElse(null);
        if (roomDb == null) return;
        RoomResponse roomResponse = new RoomResponse(roomDb);
        BaseRpc response = rpcFactory.getNotification(RpcMethods.CHAT_ROOM_CREATED_NOTIFICATION, roomResponse);
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
        BaseRpc response = rpcFactory.getNotification(RpcMethods.MESSAGE_UPDATED_NOTIFICATION, message);
        for (long user : message.getRoom().getUsers()) {
            userSocketManager.send(user, response);
        }
    }

    @Override
    public void notifyUserRefuseChat(long userId) {
        BaseRpc response = rpcFactory.getNotification(RpcMethods.USERS_REFUSE_ROOM);
        userSocketManager.send(userId, response);
    }

    @Override
    public void notifyUserShouldOpens(long userId, OpenRequest openRequest) {
        BaseRpc response = rpcFactory.getNotification(RpcMethods.USER_SHOULD_OPENS_NOTIFICATION, openRequest);
        userSocketManager.send(userId, response);
    }

    @Override
    public void notifyUserOpens(long userId, RoomBlockingStatus roomBlockingStatus) {
        BaseRpc response = rpcFactory.getNotification(RpcMethods.USER_OPENS_NOTIFICATION, roomBlockingStatus);
        userSocketManager.send(userId, response);
    }
}
