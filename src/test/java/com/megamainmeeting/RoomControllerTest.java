package com.megamainmeeting;

import com.megamainmeeting.domain.block.RoomBlockReason;
import com.megamainmeeting.domain.block.RoomBlocked;
import com.megamainmeeting.spring.base.NotificationRpcResponse;
import com.megamainmeeting.spring.base.RpcMethods;
import com.megamainmeeting.spring.controller.room.RoomBlock;
import com.megamainmeeting.spring.socket.auth.AuthenticationController;
import com.megamainmeeting.utils.TestClientManager;
import com.megamainmeeting.utils.TestValues;
import com.megamainmeeting.db.RoomRepositoryJpa;
import com.megamainmeeting.db.dto.ChatMessageDb;
import com.megamainmeeting.db.dto.RoomDb;
import com.megamainmeeting.domain.RoomRepository;
import com.megamainmeeting.entity.chat.NewChatMessage;
import com.megamainmeeting.entity.room.Room;
import com.megamainmeeting.interactor.ChatMessageInteractor;
import com.megamainmeeting.dto.RoomResponse;
import com.megamainmeeting.spring.base.BaseResponse;
import com.megamainmeeting.spring.controller.room.RoomController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
public class RoomControllerTest extends BaseTest {

    @Autowired
    RoomRepository roomRepository;
    @Autowired
    RoomRepositoryJpa roomRepositoryJpa;
    @Autowired
    ChatMessageInteractor chatMessageInteractor;
    @Autowired
    RoomController roomController;
    @Autowired
    TestValues testValues;
    @Autowired
    TestClientManager testClientManager;
    @Autowired
    AuthenticationController authenticationController;

    private static final RoomBlockReason ROOM_BLOCK_REASON = RoomBlockReason.AbusiveBehaviour;


    @Before
    public void before() {
        testValues.prepareRoomToUser1User2();
        testClientManager.clear();
    }

    @Test
    public void test1() throws Exception {
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

    private Room createRoomIfNotExist(long user1, long user2) {
        try {
            return roomRepository.getList(user1).getList().stream().filter(r -> r.isUserInRoom(user2)).findFirst().get();
        } catch (Exception e) {
            return roomRepository.create(new HashSet<>(Arrays.asList(user1, user2)));
        }
    }

    @Test
    public void blockRoom() throws Exception {
        authenticationController.auth(testValues.getAuthSocket1(), testValues.getSession1());
        authenticationController.auth(testValues.getAuthSocket2(), testValues.getSession2());
        RoomBlock roomBlock = new RoomBlock();
        roomBlock.setRoomId(TestValues.ROOM_ID);
        roomBlock.setReason(RoomBlockReason.AbusiveBehaviour);
        roomController.block(TestValues.USER_ID_1, roomBlock);
        NotificationRpcResponse<RoomBlocked> block1 = (NotificationRpcResponse<RoomBlocked>) testClientManager.removeFirst();
        NotificationRpcResponse<RoomBlocked> block2 = (NotificationRpcResponse<RoomBlocked>) testClientManager.removeFirst();
        Assert.assertEquals(RpcMethods.ROOM_BLOCKED_NOTIFICATION, block1.getMethod());
        Assert.assertEquals(RpcMethods.ROOM_BLOCKED_NOTIFICATION, block2.getMethod());
        Assert.assertEquals(TestValues.ROOM_ID, (long) block1.getParams().getRoomId());
        Assert.assertEquals(TestValues.ROOM_ID, (long) block2.getParams().getRoomId());
        Assert.assertEquals(TestValues.USER_ID_1, block1.getParams().getUserId());
        Assert.assertEquals(TestValues.USER_ID_1, block2.getParams().getUserId());
        Assert.assertEquals(ROOM_BLOCK_REASON, block1.getParams().getReason());
        Assert.assertEquals(ROOM_BLOCK_REASON, block2.getParams().getReason());
    }
}

