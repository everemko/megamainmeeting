package com.megamainmeeting.db;

import com.megamainmeeting.db.dto.RoomBlockedDb;
import com.megamainmeeting.db.dto.UserDb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomBlockedRepository extends JpaRepository<RoomBlockedDb, Long> {
}
