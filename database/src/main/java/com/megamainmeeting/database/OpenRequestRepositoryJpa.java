package com.megamainmeeting.database;

import com.megamainmeeting.database.dto.OpenRequestDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpenRequestRepositoryJpa extends JpaRepository<OpenRequestDb, Long> {
}
