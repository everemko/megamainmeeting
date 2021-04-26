package com.megamainmeeting;

import com.megamainmeeting.config.AppConfigTest;
import com.megamainmeeting.config.RepositoryConfigTest;
import com.megamainmeeting.config.TestConfigs;
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
@SpringBootTest(classes = Application.class)
@ContextConfiguration(classes = {AppConfigTest.class, RepositoryConfigTest.class, TestConfigs.class})
public class UserOpeningTest {

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
        testValues.deleteProfiles();
        testValues.prepareProfiles();
        testValues.deleteAllUser1User2Opens();
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
        NotificationRpcResponse<UserOpensSet> userShouldOpens1 = (NotificationRpcResponse<UserOpensSet>) testClientManager.removeFirst();
        Assert.assertEquals(RpcMethods.USER_SHOULD_OPENS_NOTIFICATION, userShouldOpens1.getMethod());
        Assert.assertEquals(TestValues.ROOM_ID, userShouldOpens1.getParams().getRoomId());
//        Assert.assertEquals(userShouldOpens1.getParams().getUserId(), TestValues.USER_ID_1);
        NotificationRpcResponse<UserOpensSet> userShouldOpens2 = (NotificationRpcResponse<UserOpensSet>) testClientManager.removeFirst();
        Assert.assertEquals(userShouldOpens2.getMethod(), RpcMethods.USER_SHOULD_OPENS_NOTIFICATION);
        Assert.assertEquals(userShouldOpens2.getParams().getRoomId(), TestValues.ROOM_ID);
//        Assert.assertEquals(userShouldOpens2.getParams().getUserId(), TestValues.USER_ID_2);
        userOpeningController.userOpen(TestValues.USER_ID_1, testValues.getUserOpens1());
        checkUser1OpensCorrectly();
        checkUser1OpensCorrectly();
        userOpeningController.userOpen(TestValues.USER_ID_2, testValues.getUserOpens2());
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
        UserOpensSet userOpens1 = roomBlockingStatus1.getOpens().stream().filter(it -> it.getUserId() == TestValues.USER_ID_1).findFirst().get();
        UserOpensSet userOpens2 = roomBlockingStatus1.getOpens().stream().filter(it -> it.getUserId() == TestValues.USER_ID_2).findFirst().get();
        Assert.assertEquals(1, userOpens1.getCount());
        Assert.assertEquals(0, userOpens2.getCount());
    }

    private void checkUser2OpensCorrectly(){
        NotificationRpcResponse<RoomBlockingStatus> response2 = (NotificationRpcResponse<RoomBlockingStatus>) testClientManager.removeFirst();
        RoomBlockingStatus roomBlockingStatus2 = response2.getParams();
        Assert.assertFalse(roomBlockingStatus2.isBlocked());
        Assert.assertEquals(roomBlockingStatus2.getId(), TestValues.ROOM_ID);
        UserOpensSet userOpens1 = roomBlockingStatus2.getOpens().stream().filter(it -> it.getUserId() == TestValues.USER_ID_1).findFirst().get();
        UserOpensSet userOpens2 = roomBlockingStatus2.getOpens().stream().filter(it -> it.getUserId() == TestValues.USER_ID_2).findFirst().get();
        Assert.assertEquals(1, userOpens1.getCount());
        Assert.assertEquals(1, userOpens2.getCount());
    }
}
