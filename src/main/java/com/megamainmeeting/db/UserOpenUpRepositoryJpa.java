package com.megamainmeeting.db;

import com.megamainmeeting.db.dto.ChatMessageDb;
import com.megamainmeeting.db.dto.UserOpenUpDb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOpenUpRepositoryJpa extends JpaRepository<UserOpenUpDb, Long> {
}
