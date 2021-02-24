package db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import db.dto.UserDb;

@Repository
public interface UserRepositoryJpa extends JpaRepository<UserDb, Long> {
}
