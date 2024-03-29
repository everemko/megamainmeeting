package com.megamainmeeting.db.repository;

import com.megamainmeeting.db.OpenRequestRepositoryJpa;
import com.megamainmeeting.db.RoomRepositoryJpa;
import com.megamainmeeting.db.UserOpenUpRepositoryJpa;
import com.megamainmeeting.db.UserRepositoryJpa;
import com.megamainmeeting.db.dto.*;
import com.megamainmeeting.domain.RoomRepository;
import com.megamainmeeting.domain.error.OpenRequestNotFoundException;
import com.megamainmeeting.domain.error.RoomNotFoundException;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.domain.error.UserNotInRoomException;
import com.megamainmeeting.domain.open.*;
import com.megamainmeeting.spring.controller.user.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class UserOpensRepositoryImpl implements UserOpensRepository {

    @Autowired
    RoomRepositoryJpa roomRepositoryJpa;
    @Autowired
    UserRepositoryJpa userRepositoryJpa;
    @Autowired
    OpenRequestRepositoryJpa openRequestRepositoryJpa;
    @Autowired
    UserOpenUpRepositoryJpa userOpenUpRepositoryJpa;

    @Override
    public Room getRoom(long roomId) throws RoomNotFoundException, OpenRequestNotFoundException {
        RoomDb roomDb = roomRepositoryJpa.findById(roomId).orElseThrow(RoomNotFoundException::new);
        long messageCount = roomDb.getMessages().size();
        return Room.getInstance(roomId, messageCount, getUser(roomDb), getOpenRequests(roomDb));
    }

    private Set<OpenRequest> getOpenRequests(RoomDb roomDb) {
        return roomDb.getOpenRequest()
                .stream()
                .map(it ->
                        OpenRequest.getInstance(
                                it.getId(),
                                roomDb.getId(),
                                getUserOpens(it.getUserOpen()),
                                it.getTime())
                )
                .collect(Collectors.toSet());
    }

    private Set<UserOpens> getUserOpens(Set<UserOpenUpDb> userOpens) {
        return userOpens.stream()
                .map(it -> {
                    UserOpens userOpen = new UserOpens();
                    userOpen.setType(it.getUserOpenType());
                    userOpen.setUserId(it.getUser().getId());
                    userOpen.setOpenRequestId(it.getOpenRequest().getId());
                    return userOpen;
                })
                .collect(Collectors.toSet());
    }

    private Set<User> getUser(RoomDb roomDb) {
        return roomDb.getUsers()
                .stream()
                .map((userDb) -> {
                    User user = new User();
                    user.setId(userDb.getId());
                    user.setOpens(getAllUserOpens(userDb));
                    user.setOpensUsed(getUserOpens(userDb, roomDb.getId()));
                    return user;
                })
                .collect(Collectors.toSet());
    }

    private Set<UserOpenType> getUserOpens(UserDb userDb, long roomId) {
        return userDb.getUserOpens()
                .stream()
                .filter(it -> it.getOpenRequest().getRoomId() == roomId)
                .map(UserOpenUpDb::getUserOpenType)
                .collect(Collectors.toSet());
    }

    private Set<UserOpenType> getAllUserOpens(UserDb userDb) {
        Set<UserOpenType> types = new HashSet<>();
        UserProfileDb userProfile = userDb.getUserProfile();
        if (userProfile == null) return types;
        if (userProfile.getCity() != null) {
            types.add(UserOpenType.City);
        }
        if (userProfile.getCountry() != null) {
            types.add(UserOpenType.Country);
        }
        if (userProfile.getAboutMyself() != null) {
            types.add(UserOpenType.AboutMyself);
        }
        if (userProfile.getFirstDateIdeal() != null) {
            types.add(UserOpenType.FirstDateIdeal);
        }
        if (userProfile.getHeight() != 0) {
            types.add(UserOpenType.Height);
        }
        if (userProfile.getWight() != 0) {
            types.add(UserOpenType.Wight);
        }
        if (userProfile.getProfession() != null) {
            types.add(UserOpenType.Profession);
        }
        if (userProfile.getPhoto() != null) {
            types.add(UserOpenType.Photo);
        }
        return types;
    }

    @Override
    public void updateUserOpens(UserOpens userOpens) throws RoomNotFoundException, UserNotFoundException,
            UserNotInRoomException, OpenRequestNotFoundException {
        Optional<OpenRequestDb> optionalOpenRequestDb = openRequestRepositoryJpa.findById(userOpens.getOpenRequestId());
        if (optionalOpenRequestDb.isEmpty()) throw new OpenRequestNotFoundException();
        OpenRequestDb openRequestDb = optionalOpenRequestDb.get();
        UserDb userDb = openRequestDb.getRoom().getUser(userOpens.getUserId());
        UserOpenUpDb userOpenUpDb = new UserOpenUpDb();
        userOpenUpDb.setUser(userDb);
        userOpenUpDb.setOpenRequest(openRequestDb);
        userOpenUpDb.setUserOpenType(userOpens.getType());
        userOpenUpRepositoryJpa.save(userOpenUpDb);
    }

    @Override
    public OpenRequest blockRoom(long roomId) throws RoomNotFoundException {
        Optional<RoomDb> optionalRoomDb = roomRepositoryJpa.findById(roomId);
        if (optionalRoomDb.isEmpty()) throw new RoomNotFoundException();
        RoomDb roomDb = optionalRoomDb.get();
        OpenRequestDb openRequestDb = new OpenRequestDb();
        openRequestDb.setRoom(roomDb);
        openRequestRepositoryJpa.save(openRequestDb);
        return OpenRequest.getInstance(openRequestDb.getId(), roomId, Collections.emptySet(), openRequestDb.getTime());
    }

    @Override
    public Room getRoomByOpenRequestId(long openRequestId) throws OpenRequestNotFoundException, RoomNotFoundException {
        OpenRequestDb openRequestDb = openRequestRepositoryJpa.findById(openRequestId)
                .orElseThrow(OpenRequestNotFoundException::new);
        RoomDb roomDb = openRequestDb.getRoom();
        long messageCount = roomDb.getMessages().size();
        return Room.getInstance(roomDb.getId(), messageCount, getUser(roomDb), getOpenRequests(roomDb));
    }
}
