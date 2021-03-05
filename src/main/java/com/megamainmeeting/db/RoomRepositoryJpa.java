package com.megamainmeeting.db;

import com.megamainmeeting.db.dto.RoomDb;
import com.megamainmeeting.db.dto.UserDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RoomRepositoryJpa extends JpaRepository<RoomDb, Long> {


    @Query(value = "Select T from room where user_id = :userId", nativeQuery = true)
    List<RoomDb> findAllByUserId(long userId);
}
