package com.megamainmeeting;
import com.megamainmeeting.entity.chat.ChatMessage;
import com.megamainmeeting.entity.chat.NewChatMessage;
import com.megamainmeeting.error.ErrorMessages;
import com.megamainmeeting.spring.base.BaseRequest;
import com.megamainmeeting.spring.base.BaseResponse;
import com.megamainmeeting.spring.controller.chat.ChatController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ChatControllerTest {

    private final static long ROOM_ID = 1;

    @Autowired
    ChatController chatController;


    @Test
    public void addMessageRoomNotFound(){
        NewChatMessage newChatMessage = new NewChatMessage();
        newChatMessage.setUserId(1);
        newChatMessage.setRoomId(-1);
        newChatMessage.setMessage("asdfasdf");
        BaseResponse<?> response = chatController.processMessage(newChatMessage);
        assertFalse(response.isSuccess());
        assertEquals(ErrorMessages.ROOM_NOT_FOUND, response.getErrorMessage());
    }

    @Test
    public void addMessageRoomInvalidUser(){
        NewChatMessage newChatMessage = new NewChatMessage();
        newChatMessage.setUserId(-1);
        newChatMessage.setRoomId(1);
        newChatMessage.setMessage("asdfasdf");
        BaseResponse<?> response = chatController.processMessage(newChatMessage);
        assertFalse(response.isSuccess());
        assertEquals(ErrorMessages.USER_NOT_FOUND, response.getErrorMessage());
    }

    @Test
    public void addMessageSuccess(){
        NewChatMessage newChatMessage = new NewChatMessage();
        newChatMessage.setUserId(1);
        newChatMessage.setRoomId(ROOM_ID);
        newChatMessage.setMessage("asdfasdf");
        BaseResponse<ChatMessage> response = (BaseResponse<ChatMessage>) chatController.processMessage(newChatMessage);
        assertTrue(response.isSuccess());
        assertEquals(1, response.getResult().getUserId());
        assertEquals(ROOM_ID, response.getResult().getRoom().getId());
        assertEquals("asdfasdf", response.getResult().getMessage());
    }

    @Test
    public void removeChatCandidate(){
        BaseRequest request = new BaseRequest();
        request.setUserId(1);
        BaseResponse<Object> response = chatController.addChatCandidate(request);
        BaseResponse<Object> response2 = chatController.removeChatCandidate(request);
        BaseResponse<Object> response3 = chatController.addChatCandidate(request);
        assertTrue(response3.isSuccess());
        assertNull(response3.getErrorMessage());
        assertNull(response3.getResult());
    }

    @Test
    public void addChatCandidate(){
        BaseRequest request = new BaseRequest();
        request.setUserId(1);
        BaseResponse<Object> response = chatController.addChatCandidate(request);
        assertTrue(response.isSuccess());
        assertNull(response.getErrorMessage());
        assertNull(response.getResult());
        chatController.removeChatCandidate(request);
    }

    @Test
    public void addChatCandidateInvalidUser(){
        BaseRequest request = new BaseRequest();
        request.setUserId(-1);
        BaseResponse<Object> response = chatController.addChatCandidate(request);
        assertFalse(response.isSuccess());
        assertEquals(ErrorMessages.USER_NOT_FOUND, response.getErrorMessage());
    }

    @Test
    public void addChatCandidateAlreadyInQueue(){
        BaseRequest request = new BaseRequest();
        request.setUserId(1);
        BaseResponse<Object> response = chatController.addChatCandidate(request);
        BaseResponse<Object> response2 = chatController.addChatCandidate(request);
        assertTrue(response.isSuccess());
        assertNull(response.getErrorMessage());
        assertNull(response.getResult());
        assertFalse(response2.isSuccess());
        assertEquals(ErrorMessages.USER_ALREADY_CANDIDATE, response2.getErrorMessage());
        assertNull(response2.getResult());
        chatController.removeChatCandidate(request);
    }
}
