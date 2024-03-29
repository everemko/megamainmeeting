package com.megamainmeeting.db;

import org.springframework.data.jpa.repository.JpaRepository;
import com.megamainmeeting.db.dto.SessionDb;
import org.springframework.data.jpa.repository.Query;

public interface SessionRepositoryJpa extends JpaRepository<SessionDb, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM session WHERE session.token = :token")
    SessionDb findByToken(String token);

    @Query(nativeQuery = true, value = "Select user_id from session where session.token = :token")
    long getUserId(String token);
}
