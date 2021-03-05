package com.megamainmeeting.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.megamainmeeting.db.dto.UserDb;

@Repository
public interface UserRepositoryJpa extends JpaRepository<UserDb, Long> {
}
