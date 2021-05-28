package com.megamainmeeting.db;

import com.megamainmeeting.db.dto.BoxHostingUserAvatarDb;
import com.megamainmeeting.db.dto.UserProfileDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BoxHostingUserAvatarRepositoryJpa extends JpaRepository<BoxHostingUserAvatarDb, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM box_hosting_user_avatar WHERE box_hosting_user_avatar.user_id = :userId")
    BoxHostingUserAvatarDb findByUserId(long userId);
}
