package com.megamainmeeting.db.repository;

import com.megamainmeeting.db.RoomRepositoryJpa;
import com.megamainmeeting.db.UserRepositoryJpa;
import com.megamainmeeting.db.dto.*;
import com.megamainmeeting.domain.RoomRepository;
import com.megamainmeeting.domain.error.RoomNotFoundException;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.domain.error.UserNotInRoomException;
import com.megamainmeeting.domain.open.*;
import com.megamainmeeting.spring.controller.user.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserOpensRepositoryImpl implements UserOpensRepository {

    @Autowired
    RoomRepositoryJpa roomRepositoryJpa;
    @Autowired
    UserRepositoryJpa userRepositoryJpa;

    @Override
    public Room getRoom(long roomId) throws RoomNotFoundException {
        Room room = new Room();
        Optional<RoomDb> optionalRoomDb = roomRepositoryJpa.findById(roomId);
        if (optionalRoomDb.isEmpty()) throw new RoomNotFoundException();
        RoomDb roomDb = optionalRoomDb.get();
        long messageCount = roomDb.getMessages().size();
        room.setId(roomId);
        room.setMessageCount(messageCount);
        room.setUsers(getUser(roomDb));
        return room;
    }

    private Set<User> getUser(RoomDb roomDb) {
        return roomDb.getUsers()
                .stream()
                .map((userDb) -> {
                    User user = new User();
                    user.setId(userDb.getId());
                    user.setOpens(getAllUserOpens(userDb));
                    user.setOpensUsed(userDb.getUserOpens(roomDb.getId()));
                    return user;
                })
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
    public void updateRoom(Room room) throws RoomNotFoundException, UserNotInRoomException, UserNotFoundException {
//        Optional<RoomDb> optionalRoomDb = roomRepositoryJpa.findById(room.getId());
//        if(optionalRoomDb.isEmpty()) throw new RoomNotFoundException();
//        RoomDb roomDb = optionalRoomDb.get();
//        for(User user: room.getUsers()){
//            if(!roomDb.isUserInRoom(user.getId())) continue;
//            Optional<UserDb> optionalUserDb = userRepositoryJpa.findById(user.getId());
//            if(optionalUserDb.isEmpty()) throw new UserNotFoundException();
//            UserDb userDb = optionalUserDb.get();
//            Set<UserOpenUpDb> set = userDb.getUserOpens();
//            UserOpenUpDb userOpenUpDb = new UserOpenUpDb();
//            userOpenUpDb.setRoom(roomDb);
//            userOpenUpDb.setUser(userDb);
//            userOpenUpDb.setUserOpenType();
//        }
//        roomRepositoryJpa.save(roomDb);
    }

    @Override
    public void updateUserOpens(UserOpens userOpens) throws RoomNotFoundException, UserNotFoundException, UserNotInRoomException {
        Optional<RoomDb> optionalRoomDb = roomRepositoryJpa.findById(userOpens.getRoomId());
        if (optionalRoomDb.isEmpty()) throw new RoomNotFoundException();
        RoomDb roomDb = optionalRoomDb.get();
        UserDb userDb = roomDb.getUser(userOpens.getUserId());
        UserOpenUpDb userOpenUpDb = new UserOpenUpDb();
        userOpenUpDb.setUser(userDb);
        userOpenUpDb.setRoom(roomDb);
        userOpenUpDb.setUserOpenType(userOpens.getType());
        userDb.getUserOpens().add(userOpenUpDb);
        userRepositoryJpa.save(userDb);
    }
}
