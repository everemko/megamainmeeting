package com.megamainmeeting.db;

import com.megamainmeeting.db.dto.UserPushTokenDb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPushTokenRepositoryJpa extends JpaRepository<UserPushTokenDb, Long > {
}
