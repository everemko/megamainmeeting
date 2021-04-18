package com.megamainmeeting.dto;

import com.megamainmeeting.db.dto.ChatMessageDb;
import com.megamainmeeting.db.dto.RoomDb;
import com.megamainmeeting.db.dto.RoomDeleted;
import com.megamainmeeting.db.dto.UserDb;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public class RoomResponse {

    private long id;
    private Set<Long> users = new HashSet<>();
    private LocalDateTime createdAt;
    private long messageCountUnread;
    private RoomDeleted roomDeleted;

    public RoomResponse(RoomDb roomDb){
        id = roomDb.getId();
        users = roomDb.getUsers()
                .stream()
                .map(UserDb::getId)
                .collect(Collectors.toSet());
        createdAt = roomDb.getCreatedAt();
        messageCountUnread = roomDb.getMessages()
                .stream()
                .filter(ChatMessageDb::isRead)
                .count();
        roomDeleted = roomDb.getRoomDeleted();
    }
}
