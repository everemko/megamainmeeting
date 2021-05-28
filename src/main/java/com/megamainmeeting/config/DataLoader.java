package com.megamainmeeting.config;

import com.megamainmeeting.db.ChatMessageRepositoryJpa;
import com.megamainmeeting.db.RoomRepositoryJpa;
import com.megamainmeeting.db.UserRepositoryJpa;
import com.megamainmeeting.db.dto.ChatMessageDb;
import com.megamainmeeting.db.dto.RoomDb;
import com.megamainmeeting.db.dto.UserDb;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements ApplicationRunner {

    private static final long USER_1 = 1;
    private static final long USER_2 = 2;
    private static final long ROOM_ID = 1;

    @Autowired
    UserRepositoryJpa userRepository;
    @Autowired
    RoomRepositoryJpa roomRepository;
    @Autowired
    ChatMessageRepositoryJpa chatMessageRepositoryJpa;
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        if(!userRepository.existsById(USER_1)){
//            UserDb user = new UserDb();
//            user.setId(1);
//            userRepository.save(user);
//        }
//        if(!userRepository.existsById(USER_2)){
//            UserDb user = new UserDb();
//            user.setId(2);
//            userRepository.save(user);
//        }
//        if(!roomRepository.existsById(ROOM_ID)){
//            RoomDb roomDb = new RoomDb();
//            roomDb.setId(ROOM_ID);
//            roomDb.addUser(userRepository.findById(USER_1).get());
//            roomDb.addUser(userRepository.findById(USER_2).get());
//            roomRepository.save(roomDb);
//        }
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String sql = "Update chat_messages set image_id = '-1' where image_id is null";
        NativeQuery query = session.createSQLQuery(sql);
        query.executeUpdate();
        transaction.commit();
    }
}
