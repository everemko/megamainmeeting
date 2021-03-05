package com.megamainmeeting.db;

import org.springframework.data.jpa.repository.JpaRepository;
import com.megamainmeeting.db.dto.SessionDb;
import org.springframework.data.jpa.repository.Query;

public interface SessionRepositoryJpa extends JpaRepository<SessionDb, Long> {

    @Query(nativeQuery = true, value = "SELECT T FROM session WHERE session.token = :token")
    SessionDb findByToken(String token);
}
