package com.megamainmeeting.config;

import com.megamainmeeting.TestClientManager;
import com.megamainmeeting.domain.RoomRepository;
import com.megamainmeeting.entity.chat.NewChatMessage;
import com.megamainmeeting.spring.controller.chat.ChatController;
import com.megamainmeeting.spring.socket.chat.ChatMessageOperationsController;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.web.bind.annotation.RequestAttribute;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

@AllArgsConstructor
public class TestValues {

    public static final long USER_ID_1 = 1;
    public static final long USER_ID_2 = 2;
    public static final long USER_ID_3 = 3;
    public static long ROOM_ID = 1;
    public static final String MESSAGE_TEST = "message test";

    private RoomRepository roomRepository;

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

    public void prepareSession1(){

    }

    public void prepareSession2(){

    }
}
