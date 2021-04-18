package com.megamainmeeting;

import com.megamainmeeting.domain.RoomRepository;
import com.megamainmeeting.domain.error.RoomNotFoundException;
import com.megamainmeeting.domain.error.UserAlreadyCandidateException;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.entity.chat.ChatMessage;
import com.megamainmeeting.entity.chat.NewChatMessage;
import com.megamainmeeting.domain.error.ErrorMessages;
import com.megamainmeeting.spring.base.BaseResponse;
import com.megamainmeeting.spring.controller.chat.ChatController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.HashSet;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ChatControllerTest {

    private final static long NOT_EXIST_USER_ID = -1;
    private final static long USER_ID = 1;
    private final static long USER_ID_2 = 2;
    private long roomId = -1;

    @Autowired
    ChatController chatController;
    @Autowired
    RoomRepository roomRepository;

    @Before
    public void prepare(){
        if(roomRepository.getList(USER_ID).isEmpty()){
            roomRepository.create(new HashSet<>(Collections.singletonList(USER_ID)));
        }
        roomId = roomRepository.getList(USER_ID).getList().stream().findFirst().get().getId();
    }


    @Test(expected = RoomNotFoundException.class)
    public void addMessageRoomNotFound() throws Exception {
        NewChatMessage newChatMessage = new NewChatMessage();
        newChatMessage.setRoomId(-1);
        newChatMessage.setMessage("asdfasdf");
        BaseResponse<?> response = chatController.processMessage(USER_ID, newChatMessage);
        assertFalse(response.isSuccess());
        assertEquals(ErrorMessages.ROOM_NOT_FOUND, response.getErrorMessage());
    }

    @Test(expected = UserNotFoundException.class)
    public void addMessageRoomInvalidUser() throws Exception {
        NewChatMessage newChatMessage = new NewChatMessage();
        newChatMessage.setRoomId(1);
        newChatMessage.setMessage("asdfasdf");
        BaseResponse<?> response = chatController.processMessage(NOT_EXIST_USER_ID, newChatMessage);
        assertFalse(response.isSuccess());
        assertEquals(ErrorMessages.USER_NOT_FOUND, response.getErrorMessage());
    }

    @Test
    public void addMessageSuccess() throws Exception {
        NewChatMessage newChatMessage = new NewChatMessage();
        newChatMessage.setRoomId(roomId);
        newChatMessage.setMessage("asdfasdf");
        BaseResponse<ChatMessage> response = (BaseResponse<ChatMessage>) chatController.processMessage(USER_ID, newChatMessage);
        assertTrue(response.isSuccess());
        assertEquals(1, response.getResult().getUserId());
        assertEquals(roomId, response.getResult().getRoom().getId());
        assertEquals("asdfasdf", response.getResult().getMessage());
    }

    @Test
    public void removeChatCandidate() throws Exception {
        chatController.removeChatCandidate(USER_ID);
        chatController.addChatCandidate(USER_ID);
        chatController.removeChatCandidate(USER_ID);
        BaseResponse<Object> response3 = chatController.addChatCandidate(USER_ID);
        assertTrue(response3.isSuccess());
        assertNull(response3.getErrorMessage());
        assertNull(response3.getResult());
    }

    public void addChatCandidate() throws Exception {
        BaseResponse<Object> response = chatController.addChatCandidate(USER_ID);
        assertTrue(response.isSuccess());
        assertNull(response.getErrorMessage());
        assertNull(response.getResult());
        chatController.removeChatCandidate(USER_ID);
    }

    @Test(expected = UserNotFoundException.class)
    public void addChatCandidateInvalidUser() throws Exception {
        BaseResponse<Object> response = chatController.addChatCandidate(-1);
        assertFalse(response.isSuccess());
        assertEquals(ErrorMessages.USER_NOT_FOUND, response.getErrorMessage());
    }

    @Test(expected = UserAlreadyCandidateException.class)
    public void addChatCandidateAlreadyInQueue() throws Exception {
        BaseResponse<Object> response = chatController.addChatCandidate(USER_ID);
        BaseResponse<Object> response2 = chatController.addChatCandidate(USER_ID);
        assertTrue(response.isSuccess());
        assertNull(response.getErrorMessage());
        assertNull(response.getResult());
        assertFalse(response2.isSuccess());
        assertEquals(ErrorMessages.USER_ALREADY_CANDIDATE, response2.getErrorMessage());
        assertNull(response2.getResult());
        chatController.removeChatCandidate(USER_ID);
    }
}
