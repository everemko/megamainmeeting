package db;

import org.springframework.data.jpa.repository.JpaRepository;
import db.dto.SessionDb;

public interface SessionRepositoryJpa extends JpaRepository<SessionDb, Long> {
}
