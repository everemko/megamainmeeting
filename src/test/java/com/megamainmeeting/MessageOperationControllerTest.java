package com.megamainmeeting;


import com.megamainmeeting.config.AppConfigTest;
import com.megamainmeeting.config.TestValues;
import com.megamainmeeting.dto.ReadMessageOperationDto;
import com.megamainmeeting.entity.chat.ChatMessage;
import com.megamainmeeting.entity.chat.NewChatMessage;
import com.megamainmeeting.spring.base.NotificationRpcResponse;
import com.megamainmeeting.spring.controller.chat.ChatController;
import com.megamainmeeting.spring.socket.chat.ChatMessageOperationsController;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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

    @Test
    public void test() throws Exception{
        NewChatMessage newChatMessage = new NewChatMessage();
        newChatMessage.setMessage(TestValues.MESSAGE_TEST);
        newChatMessage.setRoomId(TestValues.ROOM_ID);
        chatController.processMessage(TestValues.USER_ID_1, newChatMessage);
        NotificationRpcResponse<ChatMessage> response = (NotificationRpcResponse<ChatMessage>) testClientManager.removeFirst();
        ChatMessage chatMessage = response.getParams();
        ReadMessageOperationDto readMessageOperationDto = new ReadMessageOperationDto();
        readMessageOperationDto.setMessageId(chatMessage.getId());
        chatMessageOperationsController.handle(readMessageOperationDto, TestValues.USER_ID_2);
        NotificationRpcResponse<ChatMessage> response2 = (NotificationRpcResponse<ChatMessage>) testClientManager.removeFirst();
        Assert.assertTrue(response2.getParams().isRead());
    }
}
