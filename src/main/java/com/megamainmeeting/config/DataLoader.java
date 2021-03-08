package com.megamainmeeting.config;

import com.megamainmeeting.db.RoomRepositoryJpa;
import com.megamainmeeting.db.UserRepositoryJpa;
import com.megamainmeeting.db.dto.RoomDb;
import com.megamainmeeting.db.dto.UserDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private static final long USER_1 = 1;
    private static final long USER_2 = 2;
    private static final long ROOM_ID = 1;

    @Autowired
    UserRepositoryJpa userRepository;
    @Autowired
    RoomRepositoryJpa roomRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(!userRepository.existsById(USER_1)){
            UserDb user = new UserDb();
            user.setId(1);
            userRepository.save(user);
        }
        if(!userRepository.existsById(USER_2)){
            UserDb user = new UserDb();
            user.setId(2);
            userRepository.save(user);
        }
        if(!roomRepository.existsById(ROOM_ID)){
            RoomDb roomDb = new RoomDb();
            roomDb.setId(ROOM_ID);
            roomDb.addUser(userRepository.findById(USER_1).get());
            roomDb.addUser(userRepository.findById(USER_2).get());
            roomRepository.save(roomDb);
        }
    }
}
