package com.megamainmeeting.database;

import com.megamainmeeting.database.dto.OneValueDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OneValueDbRepositoryJpa extends JpaRepository<OneValueDb, Long> {

    public Optional<OneValueDb> findByName(String name);
}
