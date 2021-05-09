package com.megamainmeeting.utils;

import com.megamainmeeting.db.OpenRequestRepositoryJpa;
import com.megamainmeeting.db.RoomRepositoryJpa;
import com.megamainmeeting.db.UserOpenUpRepositoryJpa;
import com.megamainmeeting.db.UserRepositoryJpa;
import com.megamainmeeting.db.dto.*;
import com.megamainmeeting.domain.RoomRepository;
import com.megamainmeeting.domain.open.*;
import com.megamainmeeting.dto.AuthenticationSocketDto;
import com.megamainmeeting.entity.chat.NewChatMessage;
import com.megamainmeeting.entity.room.RoomList;
import com.megamainmeeting.entity.user.Gender;
import com.megamainmeeting.spring.controller.chat.ChatCandidateRequest;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.*;
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
    private TestWebSocketSession session1;
    private TestWebSocketSession session2;



    private final UserRepositoryJpa userRepositoryJpa;
    private final RoomRepository roomRepository;
    private final RoomRepositoryJpa roomRepositoryJpa;
    private final UserOpensRepository userOpensRepository;
    private final UserOpenUpRepositoryJpa userOpenUpRepositoryJpa;
    private final OpenRequestRepositoryJpa openRequestRepositoryJpa;

    public TestValues(UserRepositoryJpa userRepositoryJpa,
                      RoomRepository roomRepository,
                      RoomRepositoryJpa roomRepositoryJpa,
                      UserOpensRepository userOpensRepository,
                      UserOpenUpRepositoryJpa userOpenUpRepositoryJpa,
                      OpenRequestRepositoryJpa openRequestRepositoryJpa){
        this.roomRepositoryJpa = roomRepositoryJpa;
        this.userRepositoryJpa = userRepositoryJpa;
        this.roomRepository = roomRepository;
        this.userOpensRepository = userOpensRepository;
        this.userOpenUpRepositoryJpa = userOpenUpRepositoryJpa;
        this.openRequestRepositoryJpa = openRequestRepositoryJpa;

        setUserData(USER_ID_1);
        setUserData(USER_ID_2);
        setUserData(USER_ID_2);

        session1 = new TestWebSocketSession();
        session2 = new TestWebSocketSession();
    }

    private void setUserData(long userId){
        Optional<UserDb> optionalUserDb = userRepositoryJpa.findById(userId);
        UserDb userDb = new UserDb();
        userDb.setId(userId);
        if(optionalUserDb.isPresent()){
            userDb = optionalUserDb.get();
        }
        userDb.setDateBirth(LocalDateTime.of(1986, 3, 1, 0, 0, 0));
        userDb.setName("USER_1");
        userDb.setGender(Gender.MALE);
        userDb.setGenderMatch(Gender.FEMALE);
        userRepositoryJpa.save(userDb);
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

    public void clearRoomUser1User3(){
        RoomList list = roomRepository.getList(USER_ID_1);
        list.getList().stream().filter(room -> room.isUserInRoom(USER_ID_3)).forEach(room -> {
            roomRepository.delete(room.getId());
        });
    }

    public void prepareSession1(){

    }

    public void prepareSession2(){

    }

    public void deleteAllMessagesInRoom(){
        RoomDb roomDb = roomRepositoryJpa.findById(ROOM_ID).get();
        roomDb.getMessages().clear();
        roomRepositoryJpa.save(roomDb);
    }

    public void deleteProfiles(){
        UserDb userDb = userRepositoryJpa.findById(USER_ID_1).get();
        deleteUserProfile(userDb);
        UserDb userDb2 = userRepositoryJpa.findById(USER_ID_2).get();
        deleteUserProfile(userDb2);
    }

    private void deleteUserProfile(UserDb userDb){
        UserProfileDb userProfileDb = new UserProfileDb();
        if(userDb.getUserProfile() != null){
            userProfileDb.setId(userDb.getUserProfile().getId());
        }
        userProfileDb.setUser(userDb);
        userDb.setUserProfile(userProfileDb);
        userRepositoryJpa.save(userDb);
    }

    public void prepareProfiles(){
        UserDb userDb = userRepositoryJpa.findById(USER_ID_1).get();
        UserProfileDb userProfileDb = userDb.getUserProfile();
        prepareUserProfile(userProfileDb);
        UserDb userDb2 = userRepositoryJpa.findById(USER_ID_2).get();
        UserProfileDb userProfileDb2 = userDb2.getUserProfile();
        prepareUserProfile(userProfileDb2);
    }

    public void prepareUserProfile(UserProfileDb userProfileDb){
       userProfileDb.setCity("Minsk");
       userProfileDb.setCountry("BEL");
       userProfileDb.setHeight(185);
        userRepositoryJpa.save(userProfileDb.getUser());
    }

    public void deleteAllUser1User2Opens(){
        UserDb userDb = userRepositoryJpa.findById(USER_ID_1).get();
        userDb.getUserOpens().clear();
        userDb.getRooms().forEach(it -> {
            it.getOpenRequest().forEach(it2 -> it2.getUserOpen().clear());
        });
        userRepositoryJpa.save(userDb);
        UserDb userDb2 = userRepositoryJpa.findById(USER_ID_2).get();
        userDb2.getUserOpens().clear();
        userRepositoryJpa.save(userDb2);
    }

    public NewChatMessage getChatMessage(){
        NewChatMessage newChatMessage = new NewChatMessage();
        newChatMessage.setMessage(MESSAGE_TEST);
        newChatMessage.setRoomId(ROOM_ID);
        newChatMessage.setUserId(USER_ID_1);
        return newChatMessage;
    }

    public AuthenticationSocketDto getAuthSocket1(){
        return getAuthSocket(USER_ID_1);
    }

    public AuthenticationSocketDto getAuthSocket2(){
        return getAuthSocket(USER_ID_2);
    }

    public AuthenticationSocketDto getAuthSocket(long userId){
        AuthenticationSocketDto authenticationSocketDto = new AuthenticationSocketDto();
        authenticationSocketDto.setUserId(USER_ID_1);
        return authenticationSocketDto;
    }

    public UserOpens getUserOpens1(long openRequestId) throws Exception{
        Room room = userOpensRepository.getRoom(ROOM_ID);
        User user = room.getUser(USER_ID_1);
        return getUserOpens(user, openRequestId);
    }

    public UserOpens getUserOpens2(long openRequestId) throws Exception{
        Room room = userOpensRepository.getRoom(ROOM_ID);
        User user = room.getUser(USER_ID_2);
        return getUserOpens(user, openRequestId);
    }

    public UserOpens getUserOpens(User user, long openRequestId){
        UserOpens userOpens = new UserOpens();
        userOpens.setOpenRequestId(openRequestId);
        userOpens.setType(user.getAvailable().stream().findFirst().get());
        return userOpens;
    }

    public void deleteAllUserOpenRequestInRoom(){
        RoomDb roomDb = roomRepositoryJpa.findById(ROOM_ID).get();
        Set<UserOpenUpDb> openUpDbs = roomDb.getUsers().stream().flatMap(it -> it.getUserOpens().stream()).collect(Collectors.toSet());
        roomDb.getOpenRequest().clear();
        roomRepositoryJpa.save(roomDb);
    }
}
