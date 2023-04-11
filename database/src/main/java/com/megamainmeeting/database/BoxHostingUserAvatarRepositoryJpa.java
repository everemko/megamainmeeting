package com.megamainmeeting.database;

import com.megamainmeeting.database.dto.BoxHostingUserAvatarDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoxHostingUserAvatarRepositoryJpa extends JpaRepository<BoxHostingUserAvatarDb, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM box_hosting_user_avatar WHERE box_hosting_user_avatar.user_id = :userId")
    Optional<BoxHostingUserAvatarDb> findByUserId(long userId);
}
