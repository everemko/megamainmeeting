package com.megamainmeeting;


import com.megamainmeeting.config.AppConfigTest;
import com.megamainmeeting.config.TestValues;
import com.megamainmeeting.domain.RoomRepository;
import com.megamainmeeting.dto.ReadMessageOperationDto;
import com.megamainmeeting.entity.chat.ChatMessage;
import com.megamainmeeting.entity.chat.NewChatMessage;
import com.megamainmeeting.spring.base.NotificationRpcResponse;
import com.megamainmeeting.spring.controller.chat.ChatController;
import com.megamainmeeting.spring.socket.chat.ChatMessageOperationsController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@ContextConfiguration(classes = AppConfigTest.class)
public class MessageOperationControllerTest {

    @Autowired
    TestClientManager testClientManager;
    @Autowired
    ChatController chatController;
    @Autowired
    ChatMessageOperationsController chatMessageOperationsController;
    @Autowired
    RoomRepository roomRepository;

    private final static long USER_ID = 1;
    private final static long USER_ID_2 = 2;
    private long roomId = -1;

    @Before
    public void prepare(){
        if(roomRepository.getList(USER_ID).getList().stream().noneMatch(room -> room.isUserInRoom(USER_ID_2))){
            roomRepository.create(new HashSet<>(Arrays.asList(USER_ID, USER_ID_2)));
        }
        roomId = roomRepository.getList(USER_ID).getList().stream().findFirst().get().getId();
    }

    @Test
    public void test() throws Exception{

        NewChatMessage newChatMessage = new NewChatMessage();
        newChatMessage.setMessage(TestValues.MESSAGE_TEST);
        newChatMessage.setRoomId(roomId);
        chatController.processMessage(USER_ID, newChatMessage);
        NotificationRpcResponse<ChatMessage> response = (NotificationRpcResponse<ChatMessage>) testClientManager.removeFirst();
        ChatMessage chatMessage = response.getParams();
        ReadMessageOperationDto readMessageOperationDto = new ReadMessageOperationDto();
        readMessageOperationDto.setMessageId(chatMessage.getId());
        chatMessageOperationsController.handle(readMessageOperationDto, USER_ID_2);
        NotificationRpcResponse<ChatMessage> response2 = (NotificationRpcResponse<ChatMessage>) testClientManager.removeFirst();
        Assert.assertTrue(response2.getParams().isRead());
    }
}
