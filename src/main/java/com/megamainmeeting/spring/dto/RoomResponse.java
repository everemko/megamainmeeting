package com.megamainmeeting.spring.dto;

import com.megamainmeeting.database.dto.*;
import com.megamainmeeting.database.mapper.UserDbMapper;
import com.megamainmeeting.domain.block.entity.NewRoomBlock;
import com.megamainmeeting.entity.user.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class RoomResponse {

    @Autowired
    private UserDbMapper userDbMapper;
    private long id;
    private Set<User> users = new HashSet<>();
    private LocalDateTime createdAt;
    private long messageCountUnread;
    private RoomDeletedDb roomDeleted;
    private NewRoomBlock roomBlocked;

    public RoomResponse(RoomDb roomDb) {
        id = roomDb.getId();
        users = roomDb.getUsers()
                .stream()
                .map(userDbMapper::mapToUser)
                .collect(Collectors.toSet());
        createdAt = roomDb.getCreatedAt();
        messageCountUnread = roomDb.getMessages()
                .stream()
                .filter(ChatMessageDb::isRead)
                .count();
        roomDeleted = roomDb.getRoomDeleted();
        RoomBlockedDb roomBlockedDb = roomDb.getRoomBlocked();

        if (roomBlockedDb != null) {
            roomBlocked = NewRoomBlock.getBlockedInstance(
                    roomBlockedDb.getRoom().getId(),
                    roomBlockedDb.getUser().getId(),
                    roomBlockedDb.getReason());
        }

    }
}
