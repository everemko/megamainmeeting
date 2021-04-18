package com.megamainmeeting;

import com.megamainmeeting.config.AppConfigTest;
import com.megamainmeeting.config.RepositoryConfigTest;
import com.megamainmeeting.db.repository.UserChatCandidateQueueImpl;
import com.megamainmeeting.domain.RoomRepository;
import com.megamainmeeting.domain.UserChatCandidateQueue;
import com.megamainmeeting.domain.UserNotifier;
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

    @Test
    public void testFindChatWithExistRoom() throws Exception{
        Set<Long> users = new HashSet<>(Arrays.asList(USER_ID_1, USER_ID_2));
        RoomList roomList = roomRepository.getList(USER_ID_1);
        if(roomList.getList().stream().noneMatch((room -> room.isUserInRoom(USER_ID_2)))){
            roomRepository.create(users);
        }
        User user = new User(USER_ID_1);
        userChatCandidateQueue.add(user);
        User user2 = userChatCandidateQueue.findMatch(USER_ID_2);
        Assert.assertNull(user2);
        User user3 = userChatCandidateQueue.findMatch(USER_ID_3);
        Assert.assertNotNull(user3);
    }

}
