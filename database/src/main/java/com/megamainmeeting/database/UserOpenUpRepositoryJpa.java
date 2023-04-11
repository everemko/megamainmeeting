package com.megamainmeeting.database;

import com.megamainmeeting.database.dto.UserOpenUpDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserOpenUpRepositoryJpa extends JpaRepository<UserOpenUpDb, Long> {
}
