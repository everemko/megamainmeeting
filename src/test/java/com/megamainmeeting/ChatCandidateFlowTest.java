package com.megamainmeeting;

import com.megamainmeeting.dto.AuthenticationSocketDto;
import com.megamainmeeting.dto.ReadyStatusDto;
import com.megamainmeeting.dto.RoomReadyResult;
import com.megamainmeeting.entity.chat.ChatMessage;
import com.megamainmeeting.entity.chat.NewChatMessage;
import com.megamainmeeting.spring.base.*;
import com.megamainmeeting.spring.config.AppConfigTest;
import com.megamainmeeting.spring.controller.chat.ChatController;
import com.megamainmeeting.spring.socket.auth.AuthenticationController;
import com.megamainmeeting.spring.socket.chat.ChatCandidateController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@ContextConfiguration(classes = AppConfigTest.class)
public class ChatCandidateFlowTest {

    private static final long USER_ID_1 = 1;
    private static final long USER_ID_2 = 2;
    private static final String MESSAGE_TEST = "message test";

    private final RpcFactory rpcFactory = new RpcFactory();
    @Autowired
    TestClientManager testClientManager;
    @Autowired
    AuthenticationController authenticationController;
    @Autowired
    ChatController chatController;
    @Autowired
    ChatCandidateController chatCandidateController;
    private TestWebSocketSession session1;
    private TestWebSocketSession session2;

    private long roomId = -1;

    @Before
    public void setup() {
        session1 = new TestWebSocketSession();
        session2 = new TestWebSocketSession();
    }

    @Test
    public void test() throws Exception {
        AuthenticationSocketDto authenticationSocketDto = new AuthenticationSocketDto();
        authenticationSocketDto.setUserId(USER_ID_1);
        authenticationController.auth(authenticationSocketDto, session1);
        authenticationSocketDto.setUserId(USER_ID_2);
        authenticationController.auth(authenticationSocketDto, session2);
        BaseRequest chatCandidateRequest = new BaseRequest();
        chatCandidateRequest.setUserId(USER_ID_1);
        chatController.addChatCandidate(chatCandidateRequest);
        chatCandidateRequest.setUserId(USER_ID_2);
        chatController.addChatCandidate(chatCandidateRequest);
        checkUserMatchFound();
        ReadyStatusDto readyStatusDto = new ReadyStatusDto();
        readyStatusDto.setReady(true);
        chatCandidateController.userStatus(readyStatusDto, session1);
        chatCandidateController.userStatus(readyStatusDto, session2);
        checkRoomReady();
        NewChatMessage newChatMessage = new NewChatMessage();
        newChatMessage.setMessage(MESSAGE_TEST);
        newChatMessage.setRoomId(roomId);
        newChatMessage.setUserId(USER_ID_1);
        chatController.processMessage(newChatMessage);
        newChatMessage.setUserId(USER_ID_2);
        chatController.processMessage(newChatMessage);
        checkMessage();
    }

    private void checkUserMatchFound() {
        NotificationRpcResponse<?> rpc1 = (NotificationRpcResponse<?>) testClientManager.removeFirst();
        NotificationRpcResponse<?> rpc2 =  (NotificationRpcResponse<?>) testClientManager.removeFirst();
        Assert.assertEquals(rpc1.getMethod(), RpcMethods.USER_MATCH_FOUND_NOTIFICATION);
        Assert.assertEquals(rpc2.getMethod(), RpcMethods.USER_MATCH_FOUND_NOTIFICATION);
    }

    private void checkRoomReady(){
        NotificationRpcResponse<RoomReadyResult> rpc1 = (NotificationRpcResponse<RoomReadyResult>) testClientManager.removeFirst();;
        roomId = rpc1.getParams().getRoomId();
        NotificationRpcResponse<RoomReadyResult> rpc2 = (NotificationRpcResponse<RoomReadyResult>) testClientManager.removeFirst();;
    }

    private void checkMessage(){
        ChatMessage message1 = ((NotificationRpcResponse<ChatMessage>) testClientManager.removeFirst()).getParams();
        Assert.assertEquals(message1.getRoom().getId(), roomId);
        Assert.assertEquals(message1.getMessage(), MESSAGE_TEST);
        Assert.assertEquals(message1.getUserId(), USER_ID_1);
        ChatMessage message2 = ((NotificationRpcResponse<ChatMessage>) testClientManager.removeFirst()).getParams();
        Assert.assertEquals(message2.getRoom().getId(), roomId);
        Assert.assertEquals(message2.getMessage(), MESSAGE_TEST);
        Assert.assertEquals(message2.getUserId(), USER_ID_2);
    }
}
