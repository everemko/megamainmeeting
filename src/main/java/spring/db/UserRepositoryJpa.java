package spring.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.db.dto.UserDto;

@Repository
public interface UserRepositoryJpa extends JpaRepository<UserDto, Long> {
}
