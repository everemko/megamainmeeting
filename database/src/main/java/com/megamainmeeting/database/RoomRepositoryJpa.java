package com.megamainmeeting.database;

import com.megamainmeeting.database.dto.RoomDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepositoryJpa extends JpaRepository<RoomDb, Long> {


    @Query(value = "Select * from Room inner join room_user on room.id = room_user.room_id where room_user.user_id = :userId", nativeQuery = true)
    List<RoomDb> findAllByUserId(long userId);
}
