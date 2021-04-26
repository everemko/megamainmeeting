package com.megamainmeeting;

import com.megamainmeeting.domain.error.*;
import com.megamainmeeting.utils.TestValues;
import com.megamainmeeting.db.UserRepositoryJpa;
import com.megamainmeeting.domain.RoomRepository;
import com.megamainmeeting.entity.chat.ChatMessage;
import com.megamainmeeting.entity.chat.NewChatMessage;
import com.megamainmeeting.spring.base.BaseResponse;
import com.megamainmeeting.spring.controller.chat.ChatController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class ChatControllerTest extends BaseTest{

    private final static long NOT_EXIST_USER_ID = -1;

    @Autowired
    ChatController chatController;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    UserRepositoryJpa userRepositoryJpa;

    @Autowired
    private TestValues testValues;

    @Before
    public void prepare(){
        testValues.prepareChatCandidateReqeusts();
        testValues.prepareRoomToUser1User2();
        testValues.deleteAllMessagesInRoom();
    }


    @Test(expected = RoomNotFoundException.class)
    public void addMessageRoomNotFound() throws Exception {
        NewChatMessage newChatMessage = new NewChatMessage();
        newChatMessage.setRoomId(-1);
        newChatMessage.setMessage("asdfasdf");
        BaseResponse<?> response = chatController.processMessage(TestValues.USER_ID_1, newChatMessage);
        assertFalse(response.isSuccess());
        assertEquals(ErrorMessages.ROOM_NOT_FOUND, response.getErrorMessage());
    }

    @Test(expected = UserNotFoundException.class)
    public void addMessageRoomInvalidUser() throws Exception {
        NewChatMessage newChatMessage = new NewChatMessage();
        newChatMessage.setRoomId(TestValues.ROOM_ID);
        newChatMessage.setMessage("asdfasdf");
        BaseResponse<?> response = chatController.processMessage(NOT_EXIST_USER_ID, newChatMessage);
        assertFalse(response.isSuccess());
        assertEquals(ErrorMessages.USER_NOT_FOUND, response.getErrorMessage());
    }

    @Test
    public void addMessageSuccess() throws Exception {
        NewChatMessage newChatMessage = new NewChatMessage();
        newChatMessage.setRoomId(TestValues.ROOM_ID);
        newChatMessage.setMessage("asdfasdf");
        BaseResponse<ChatMessage> response = (BaseResponse<ChatMessage>) chatController.processMessage(TestValues.USER_ID_1, newChatMessage);
        assertTrue(response.isSuccess());
        assertEquals(1, response.getResult().getUserId());
        assertEquals(TestValues.ROOM_ID, response.getResult().getRoom().getId());
        assertEquals("asdfasdf", response.getResult().getMessage());
    }

    @Test
    public void removeChatCandidate() throws Exception {
        chatController.removeChatCandidate(TestValues.USER_ID_1);
        chatController.addChatCandidate(TestValues.USER_ID_1, testValues.getChatCandidate1());
        chatController.removeChatCandidate(TestValues.USER_ID_1);
        BaseResponse<Object> response3 = chatController.addChatCandidate(TestValues.USER_ID_1, testValues.getChatCandidate1());
        assertTrue(response3.isSuccess());
        assertNull(response3.getErrorMessage());
        assertNull(response3.getResult());
    }

    public void addChatCandidate() throws Exception {
        BaseResponse<Object> response = chatController.addChatCandidate(TestValues.USER_ID_1, testValues.getChatCandidate1());
        assertTrue(response.isSuccess());
        assertNull(response.getErrorMessage());
        assertNull(response.getResult());
        chatController.removeChatCandidate(TestValues.USER_ID_1);
    }

    @Test(expected = UserNotFoundException.class)
    public void addChatCandidateInvalidUser() throws Exception {
        BaseResponse<Object> response = chatController.addChatCandidate(-1, testValues.getChatCandidate1());
        assertFalse(response.isSuccess());
        assertEquals(ErrorMessages.USER_NOT_FOUND, response.getErrorMessage());
    }

    @Test(expected = UserAlreadyCandidateException.class)
    public void addChatCandidateAlreadyInQueue() throws Exception {
        testValues.clearRoomUser1User2();
        BaseResponse<Object> response = chatController.addChatCandidate(TestValues.USER_ID_1, testValues.getChatCandidate1());
        BaseResponse<Object> response2 = chatController.addChatCandidate(TestValues.USER_ID_1, testValues.getChatCandidate1());
        assertTrue(response.isSuccess());
        assertNull(response.getErrorMessage());
        assertNull(response.getResult());
        assertFalse(response2.isSuccess());
        assertEquals(ErrorMessages.USER_ALREADY_CANDIDATE, response2.getErrorMessage());
        assertNull(response2.getResult());
        chatController.removeChatCandidate(TestValues.USER_ID_1);
    }
}
