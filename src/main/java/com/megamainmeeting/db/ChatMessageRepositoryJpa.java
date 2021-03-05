package com.megamainmeeting.db;

import com.megamainmeeting.db.dto.ChatMessageDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface ChatMessageRepositoryJpa extends JpaRepository<ChatMessageDb, Long> {
}
