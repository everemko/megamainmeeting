package com.megamainmeeting;

import com.megamainmeeting.domain.open.*;
import com.megamainmeeting.spring.base.BaseRpc;
import com.megamainmeeting.spring.base.NotificationRpcResponse;
import com.megamainmeeting.spring.base.RpcMethods;
import com.megamainmeeting.spring.controller.opening.UserOpeningController;
import com.megamainmeeting.utils.TestValues;
import com.megamainmeeting.interactor.ChatMessageInteractor;
import com.megamainmeeting.spring.socket.auth.AuthenticationController;
import com.megamainmeeting.utils.TestClientManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserOpeningTest extends BaseTest{

    @Autowired
    TestValues testValues;
    @Autowired
    UserOpeningCheck userOpeningCheck;
    @Autowired
    ChatMessageInteractor chatMessageInteractor;
    @Autowired
    AuthenticationController authenticationController;
    @Autowired
    TestClientManager testClientManager;
    @Autowired
    UserOpeningController userOpeningController;


    @Before
    public void before() {
        testValues.prepareRoomToUser1User2();
        testValues.deleteAllMessagesInRoom();
        testValues.deleteAllUser1User2Opens();
        testValues.deleteAllUserOpenRequestInRoom();
        testValues.deleteProfiles();
        testValues.prepareProfiles();
        testClientManager.clear();
    }

    @Test
    public void test() throws Exception {
        authenticationController.auth(testValues.getAuthSocket1(), testValues.getSession1());
        authenticationController.auth(testValues.getAuthSocket2(), testValues.getSession2());
        chatMessageInteractor.onNewMessage(testValues.getChatMessage());
        chatMessageInteractor.onNewMessage(testValues.getChatMessage());
        chatMessageInteractor.onNewMessage(testValues.getChatMessage());
        removeNewMessageNotification(3);
        NotificationRpcResponse<OpenRequest> userShouldOpens1 = (NotificationRpcResponse<OpenRequest>) testClientManager.removeFirst();
        Assert.assertEquals(RpcMethods.USER_SHOULD_OPENS_NOTIFICATION, userShouldOpens1.getMethod());
        Assert.assertEquals(TestValues.ROOM_ID, userShouldOpens1.getParams().getRoomId());

        NotificationRpcResponse<OpenRequest> userShouldOpens2 = (NotificationRpcResponse<OpenRequest>) testClientManager.removeFirst();
        Assert.assertEquals(RpcMethods.USER_SHOULD_OPENS_NOTIFICATION, userShouldOpens2.getMethod());
        Assert.assertEquals(TestValues.ROOM_ID, userShouldOpens2.getParams().getRoomId());
        Assert.assertEquals(userShouldOpens2.getParams().getId(), userShouldOpens1.getParams().getId());

        userOpeningController.userOpen(TestValues.USER_ID_1, testValues.getUserOpens1(userShouldOpens1.getParams().getId()));
        checkUser1OpensCorrectly();
        checkUser1OpensCorrectly();
        userOpeningController.userOpen(TestValues.USER_ID_2, testValues.getUserOpens2(userShouldOpens1.getParams().getId()));
        checkUser2OpensCorrectly();
        checkUser2OpensCorrectly();
    }

    private void removeNewMessageNotification(long count){
        for (int i = 0; i < count; i++){
            BaseRpc userShouldOpens1 = (NotificationRpcResponse<UserOpensSet>) testClientManager.removeFirst();
            Assert.assertEquals(RpcMethods.NEW_MESSAGE_NOTIFICATION, userShouldOpens1.getMethod());
        }
    }

    private void checkUser1OpensCorrectly(){
        NotificationRpcResponse<RoomBlockingStatus> response = (NotificationRpcResponse<RoomBlockingStatus>) testClientManager.removeFirst();
        RoomBlockingStatus roomBlockingStatus1 = response.getParams();
        Assert.assertTrue(roomBlockingStatus1.isBlocked());
        Assert.assertEquals(roomBlockingStatus1.getId(), TestValues.ROOM_ID);
        Assert.assertEquals(1, roomBlockingStatus1.getByUserId(TestValues.USER_ID_1).size());
        Assert.assertEquals(0, roomBlockingStatus1.getByUserId(TestValues.USER_ID_2).size());
    }

    private void checkUser2OpensCorrectly(){
        NotificationRpcResponse<RoomBlockingStatus> response2 = (NotificationRpcResponse<RoomBlockingStatus>) testClientManager.removeFirst();
        RoomBlockingStatus roomBlockingStatus2 = response2.getParams();
        Assert.assertFalse(roomBlockingStatus2.isBlocked());
        Assert.assertEquals(roomBlockingStatus2.getId(), TestValues.ROOM_ID);
        Assert.assertEquals(1, roomBlockingStatus2.getByUserId(TestValues.USER_ID_1).size());
        Assert.assertEquals(1, roomBlockingStatus2.getByUserId(TestValues.USER_ID_2).size());
    }
}
