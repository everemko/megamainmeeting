package com.megamainmeeting.dto;

import com.megamainmeeting.db.dto.*;
import com.megamainmeeting.domain.block.RoomBlocked;
import com.megamainmeeting.entity.user.User;
import com.megamainmeeting.spring.controller.room.RoomBlock;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public class RoomResponse {

    private long id;
    private Set<User> users = new HashSet<>();
    private LocalDateTime createdAt;
    private long messageCountUnread;
    private RoomDeleted roomDeleted;
    private RoomBlocked roomBlocked;

    public RoomResponse(RoomDb roomDb) {
        id = roomDb.getId();
        users = roomDb.getUsers()
                .stream()
                .map(UserDb::toDomain)
                .collect(Collectors.toSet());
        createdAt = roomDb.getCreatedAt();
        messageCountUnread = roomDb.getMessages()
                .stream()
                .filter(ChatMessageDb::isRead)
                .count();
        roomDeleted = roomDb.getRoomDeleted();
        RoomBlockedDb roomBlockedDb = roomDb.getRoomBlocked();

        if (roomBlockedDb != null) {
            roomBlocked = RoomBlocked.getInstance(
                    roomBlockedDb.getRoom().getId(),
                    roomBlockedDb.getUser().getId(),
                    roomBlockedDb.getReason());
        }

    }
}
