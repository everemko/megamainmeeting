package com.megamainmeeting.database;

import com.megamainmeeting.database.dto.RoomBlockedDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoomBlockedRepositoryJpa extends JpaRepository<RoomBlockedDb, Long> {

    @Query(nativeQuery = true, value = "Select * from room_blocked where room_id = :roomId")
    public RoomBlockedDb findByRoomId(long roomId);
}
