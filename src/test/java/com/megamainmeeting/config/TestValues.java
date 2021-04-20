package com.megamainmeeting.config;

import com.megamainmeeting.TestClientManager;
import com.megamainmeeting.db.UserRepositoryJpa;
import com.megamainmeeting.db.dto.UserDb;
import com.megamainmeeting.domain.RoomRepository;
import com.megamainmeeting.domain.UserRepository;
import com.megamainmeeting.domain.match.ChatCandidate;
import com.megamainmeeting.domain.match.ChatGoal;
import com.megamainmeeting.entity.chat.NewChatMessage;
import com.megamainmeeting.entity.room.RoomList;
import com.megamainmeeting.spring.controller.chat.ChatCandidateRequest;
import com.megamainmeeting.spring.controller.chat.ChatController;
import com.megamainmeeting.spring.socket.chat.ChatMessageOperationsController;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.web.bind.annotation.RequestAttribute;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

@Data
public class TestValues {

    public static final long USER_ID_1 = 1;
    public static final long USER_ID_2 = 2;
    public static final long USER_ID_3 = 3;
    public static long ROOM_ID = 1;
    public static final String MESSAGE_TEST = "message test";
    private ChatCandidateRequest chatCandidate1;
    private ChatCandidateRequest chatCandidate2;



    private final UserRepositoryJpa userRepositoryJpa;
    private final RoomRepository roomRepository;

    public TestValues(UserRepositoryJpa userRepositoryJpa, RoomRepository roomRepository){
        this.userRepositoryJpa = userRepositoryJpa;
        this.roomRepository = roomRepository;
        UserDb userDb1 = new UserDb();
        userDb1.setId(USER_ID_1);
        userDb1.setDateBirth(LocalDateTime.of(1986, 3, 1, 0, 0, 0));
        userDb1.setName("USER_1");
        userDb1.setGender(0L);
        userDb1.setGenderMatch(1L);
        userRepositoryJpa.save(userDb1);
        userDb1.setId(USER_ID_2);
        userRepositoryJpa.save(userDb1);
        userDb1.setId(USER_ID_3);
        userRepositoryJpa.save(userDb1);
    }

    public void prepareRoomToUser1User2(){
        if(roomRepository.getList(USER_ID_1).getList().stream().noneMatch(room -> room.isUserInRoom(USER_ID_2))){
            roomRepository.create(new HashSet<>(Arrays.asList(USER_ID_1, USER_ID_2)));
        }
        ROOM_ID = roomRepository.getList(USER_ID_1)
                .getList()
                .stream()
                .filter(room -> room.isUserInRoom(USER_ID_2))
                .collect(Collectors.toList())
                .stream()
                .findFirst()
                .get()
                .getId();
    }

    public void prepareChatCandidateReqeusts(){
        chatCandidate1 = generateChatCandidate();
        chatCandidate2 = generateChatCandidate();
    }

    private ChatCandidateRequest generateChatCandidate(){
        ChatCandidateRequest chatCandidate = new ChatCandidateRequest();
        chatCandidate.setAgeTo(60);
        chatCandidate.setAgeFrom(18);
        chatCandidate.setGoal(0);
        return chatCandidate;
    }

    public void clearRoomUser1User2(){
        RoomList list = roomRepository.getList(USER_ID_1);
        list.getList().stream().filter(room -> room.isUserInRoom(USER_ID_2)).forEach(room -> {
            roomRepository.delete(room.getId());
        });
    }

    public void prepareSession1(){

    }

    public void prepareSession2(){

    }
}
