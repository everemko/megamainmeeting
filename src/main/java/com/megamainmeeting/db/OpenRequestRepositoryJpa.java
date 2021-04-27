package com.megamainmeeting.db;

import com.megamainmeeting.db.dto.OpenRequestDb;
import com.megamainmeeting.db.dto.RoomDb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpenRequestRepositoryJpa extends JpaRepository<OpenRequestDb, Long> {
}
