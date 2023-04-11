package com.megamainmeeting.database;

import com.megamainmeeting.database.dto.UserDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryJpa extends JpaRepository<UserDb, Long> {
}
