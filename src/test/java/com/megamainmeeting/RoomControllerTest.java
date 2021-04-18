package com.megamainmeeting;

import com.megamainmeeting.config.AppConfigTest;
import com.megamainmeeting.config.RepositoryConfigTest;
import com.megamainmeeting.config.TestValues;
import com.megamainmeeting.db.RoomRepositoryJpa;
import com.megamainmeeting.db.dto.ChatMessageDb;
import com.megamainmeeting.db.dto.RoomDb;
import com.megamainmeeting.domain.RoomRepository;
import com.megamainmeeting.entity.chat.NewChatMessage;
import com.megamainmeeting.entity.room.Room;
import com.megamainmeeting.interactor.ChatMessageInteractor;
import com.megamainmeeting.response.RoomResponse;
import com.megamainmeeting.spring.base.BaseResponse;
import com.megamainmeeting.spring.controller.room.RoomController;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@ContextConfiguration(classes = {AppConfigTest.class, RepositoryConfigTest.class})
public class RoomControllerTest {

    @Autowired
    RoomRepository roomRepository;
    @Autowired
    RoomRepositoryJpa roomRepositoryJpa;
    @Autowired
    ChatMessageInteractor chatMessageInteractor;
    @Autowired
    RoomController roomController;

    @Test
    public void test1() throws Exception{
        Room room = createRoomIfNotExist(TestValues.USER_ID_1, TestValues.USER_ID_2);
        NewChatMessage newChatMessage = new NewChatMessage();
        newChatMessage.setUserId(TestValues.USER_ID_1);
        newChatMessage.setRoomId(room.getId());
        newChatMessage.setMessage(TestValues.MESSAGE_TEST);
        chatMessageInteractor.onNewMessage(newChatMessage);
        RoomDb roomUpdated = roomRepositoryJpa.findById(room.getId()).get();
        BaseResponse<List<RoomResponse>> response = roomController.getRooms(TestValues.USER_ID_1);
        RoomResponse roomResponseFind = response.getResult().stream().filter(roomResponse -> roomResponse.getId() == room.getId()).findFirst().get();
        Assert.assertEquals(roomResponseFind.getMessageCountUnread(), roomUpdated.getMessages().stream().filter(ChatMessageDb::isRead).count());
    }

    private Room createRoomIfNotExist(long user1, long user2){
        try {
            return roomRepository.getList(user1).getList().stream().filter(r -> r.isUserInRoom(user2)).findFirst().get();
        } catch (Exception e){
            return roomRepository.create(new HashSet<>(Arrays.asList(user1, user2)));
        }
    }
}

