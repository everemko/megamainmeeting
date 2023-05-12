package com.megamainmeeting.database.chatmessage;

import com.megamainmeeting.database.Application;
import com.megamainmeeting.domain.ChatMessageRepository;
import com.megamainmeeting.domain.RoomRepository;
import com.megamainmeeting.domain.UserRepository;
import com.megamainmeeting.domain.error.ChatMessageNotFoundException;
import com.megamainmeeting.domain.error.RoomNotFoundException;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.domain.messaging.entity.ChatMessage;
import com.megamainmeeting.domain.messaging.entity.NewChatMessage;
import com.megamainmeeting.entity.room.Room;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.io.IOException;

import static com.megamainmeeting.database.room.RoomConstants.USERS_1_2;
import static com.megamainmeeting.database.user.UserConstants.USER_1;
import static com.megamainmeeting.database.user.UserConstants.USER_2;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ChatMessageRepositoryTest {

    public static final byte[] IMAGE = {0, 1, 2, 34,};

    @Inject
    RoomRepository roomRepository;
    @Inject
    UserRepository userRepository;
    @Inject
    ChatMessageRepository chatMessageRepository;

    @Test
    public void test() throws UserNotFoundException, RoomNotFoundException, IOException, ChatMessageNotFoundException {
        userRepository.saveUser(USER_1);
        userRepository.saveUser(USER_2);
        Room roomCreated = roomRepository.create(USERS_1_2);
        NewChatMessage messageForSaving = new NewChatMessage();
        messageForSaving.setUserId(USER_1.getId());
        messageForSaving.setRoomId(roomCreated.getId());
        messageForSaving.setMessage(ChatMessageConstants.MESSAGE_1);
        messageForSaving.setImage(IMAGE);
        ChatMessage messageSaved = chatMessageRepository.save(messageForSaving);
        ChatMessage messageAfterSaving = chatMessageRepository.get(messageSaved.getId());
        Assert.assertEquals(messageSaved, messageAfterSaving);
        Assert.assertEquals(messageForSaving.getMessage(), messageAfterSaving.getMessage());
        Assert.assertEquals(messageForSaving.getRoomId(), messageAfterSaving.getRoom().getId());
        Assert.assertEquals(messageForSaving.getUserId(), messageAfterSaving.getUserId());
        Assert.assertTrue(messageAfterSaving.isHasImage());
    }
}
