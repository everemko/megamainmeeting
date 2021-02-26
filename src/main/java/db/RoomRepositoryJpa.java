package db;

import db.dto.RoomDb;
import domain.entity.chat.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepositoryJpa extends JpaRepository<RoomDb, Long> {

}
