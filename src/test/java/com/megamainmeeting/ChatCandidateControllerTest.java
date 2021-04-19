package com.megamainmeeting;

import com.megamainmeeting.config.AppConfigTest;
import com.megamainmeeting.config.RepositoryConfigTest;
import com.megamainmeeting.config.TestValues;
import com.megamainmeeting.db.repository.UserChatCandidateQueueImpl;
import com.megamainmeeting.domain.RoomRepository;
import com.megamainmeeting.domain.UserChatCandidateQueue;
import com.megamainmeeting.domain.UserNotifier;
import com.megamainmeeting.domain.match.ChatCandidate;
import com.megamainmeeting.domain.match.ChatGoal;
import com.megamainmeeting.dto.ReadyStatusDto;
import com.megamainmeeting.entity.room.RoomList;
import com.megamainmeeting.entity.user.User;
import com.megamainmeeting.spring.base.NotificationRpcResponse;
import com.megamainmeeting.spring.controller.chat.ChatController;
import com.megamainmeeting.spring.socket.chat.ChatCandidateController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@ContextConfiguration(classes = {AppConfigTest.class, RepositoryConfigTest.class})
public class ChatCandidateControllerTest {

    private static final long USER_ID_1 = 1;
    private static final long USER_ID_2 = 2;
    private static final long USER_ID_3 = 3;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    UserChatCandidateQueue userChatCandidateQueue;

    TestValues testValues;
    ChatCandidate chatCandidate1;
    ChatCandidate chatCandidate2;
    ChatCandidate chatCandidate3;

    @Before
    public void preapre(){
        testValues = new TestValues(roomRepository);
        testValues.prepareRoomToUser1User2();
        chatCandidate1 = getChatCandidate(USER_ID_1);
        chatCandidate2 = getChatCandidate(USER_ID_2);
        chatCandidate3 = getChatCandidate(USER_ID_3);
    }

    private ChatCandidate getChatCandidate(long userId){
        ChatCandidate chatCandidate = new ChatCandidate();
        chatCandidate.setUserId(userId);
        chatCandidate.setAgeFrom(18);
        chatCandidate.setAgeTo(22);
        chatCandidate.setAge(20);
        chatCandidate.setChatGoal(ChatGoal.Chat);
        chatCandidate.setRoomList(roomRepository.getList(userId));
        return chatCandidate;
    }

    @Test
    public void testFindChatWithExistRoom() throws Exception{
        userChatCandidateQueue.add(chatCandidate1);
        ChatCandidate user2 = userChatCandidateQueue.findMatch(chatCandidate2);
        Assert.assertNull(user2);
        ChatCandidate user3 = userChatCandidateQueue.findMatch(chatCandidate3);
        Assert.assertNotNull(user3);
        userChatCandidateQueue.remove(USER_ID_1);
        userChatCandidateQueue.remove(USER_ID_2);
        userChatCandidateQueue.remove(USER_ID_3);
    }

}
