package com.megamainmeeting.database;

import com.megamainmeeting.database.dto.BoxHostingImageDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoxHostingImageRepositoryJpa extends JpaRepository<BoxHostingImageDb, Long> {
}
