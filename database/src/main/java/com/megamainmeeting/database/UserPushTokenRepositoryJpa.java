package com.megamainmeeting.database;

import com.megamainmeeting.database.dto.UserPushTokenDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPushTokenRepositoryJpa extends JpaRepository<UserPushTokenDb, Long > {

    @Query(nativeQuery = true, value = "SELECT * FROM user_push_token WHERE user_push_token.user_id = :userId")
    List<UserPushTokenDb> getByUserId(long userId);

    @Query(nativeQuery = true, value = "SELECT * FROM user_push_token WHERE user_push_token.token = :token")
    UserPushTokenDb getByToken(String token);
}
