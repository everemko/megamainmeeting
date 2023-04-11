package com.megamainmeeting.database;


import com.megamainmeeting.database.dto.ChatMessageDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepositoryJpa extends JpaRepository<ChatMessageDb, Long> {
}
