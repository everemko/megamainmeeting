package com.megamainmeeting.config;

import com.megamainmeeting.TestClientManager;
import com.megamainmeeting.entity.chat.NewChatMessage;
import com.megamainmeeting.spring.controller.chat.ChatController;
import com.megamainmeeting.spring.socket.chat.ChatMessageOperationsController;
import org.springframework.beans.factory.annotation.Autowired;

public class TestValues {

    public static final long USER_ID_1 = 1;
    public static final long USER_ID_2 = 2;
    public static final long USER_ID_3 = 3;
    public static final long ROOM_ID = 1;
    public static final String MESSAGE_TEST = "message test";
}
