package spring.db;

import domain.entity.auth.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import spring.db.dto.SessionDto;

public interface SessionRepositoryJpa extends JpaRepository<SessionDto, Long> {
}
