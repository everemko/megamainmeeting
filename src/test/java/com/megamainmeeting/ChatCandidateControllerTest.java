package com.megamainmeeting;

import com.megamainmeeting.config.AppConfigTest;
import com.megamainmeeting.config.TestConfigs;
import com.megamainmeeting.utils.TestValues;
import com.megamainmeeting.db.UserRepositoryJpa;
import com.megamainmeeting.domain.RoomRepository;
import com.megamainmeeting.domain.UserChatCandidateQueue;
import com.megamainmeeting.domain.match.ChatCandidate;
import com.megamainmeeting.domain.match.ChatGoal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
public class ChatCandidateControllerTest extends BaseTest{

    private static final long USER_ID_1 = 1;
    private static final long USER_ID_2 = 2;
    private static final long USER_ID_3 = 3;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    UserChatCandidateQueue userChatCandidateQueue;
    @Autowired
    UserRepositoryJpa userRepositoryJpa;

    @Autowired
    TestValues testValues;
    ChatCandidate chatCandidate1;
    ChatCandidate chatCandidate2;
    ChatCandidate chatCandidate3;

    @Before
    public void preapre(){
        testValues.prepareRoomToUser1User2();
        testValues.clearRoomUser1User3();
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
