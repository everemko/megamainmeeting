package com.megamainmeeting;

import com.megamainmeeting.domain.messaging.entity.ChatMessage;
import com.megamainmeeting.entity.room.Room;
import com.megamainmeeting.entity.user.Gender;
import com.megamainmeeting.entity.user.User;
import com.megamainmeeting.push.FirebaseClient;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class PushTest {

    private final long USER_1 = 37L;
    private final long USER_2 = 38L;

    static final FirebaseClient firebaseClient = new FirebaseClient(LoggerFactory.getLogger("pushTest"), new UserPushRepository());

    @Test
    public void sendMessageWithImage() {
        Set<Long> users = new HashSet<>();
        users.add(USER_1);
        users.add(USER_2);
        Room room = new Room();
        room.setId(0);
        room.setUsers(users);
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setUserId(USER_1);
        chatMessage.setRoom(room);
        chatMessage.setImageUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/a/aa/Philips_PM5544.svg/448px-Philips_PM5544.svg.png");
        chatMessage.setMessage("Message");
        User sender = new User(USER_1, Gender.MALE, "Vasia", LocalDateTime.now(), Gender.FEMALE);
        firebaseClient.sendMessage(chatMessage, sender);
    }

    @Test
    public void setSimpleMessage(){
        Set<Long> users = new HashSet<>();
        users.add(USER_1);
        users.add(USER_2);
        Room room = new Room();
        room.setId(0);
        room.setUsers(users);
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setUserId(USER_1);
        chatMessage.setRoom(room);;
        chatMessage.setMessage("Message");
        User sender = new User(USER_1, Gender.MALE, "Vasia", LocalDateTime.now(), Gender.FEMALE);
        firebaseClient.sendMessage(chatMessage, sender);
    }
}
