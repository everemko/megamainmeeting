package com.megamainmeeting.domain.messaging;

import com.megamainmeeting.domain.UserRepository;
import com.megamainmeeting.domain.error.BadDataException;
import com.megamainmeeting.domain.messaging.entity.NewChatMessage;
import com.megamainmeeting.domain.open.Room;
import com.megamainmeeting.domain.open.UserOpensRepository;
import com.megamainmeeting.entity.user.User;

import javax.inject.Inject;

public class NewMessageValidator {

    @Inject
    private UserRepository userRepositoryJpa;
    @Inject
    private UserOpensRepository userOpensRepository;

    void validate(NewChatMessage newMessage) throws BadDataException{
        if (newMessage.getUserId() == -1) throw new BadDataException();
        if (newMessage.getRoomId() == -1) throw new BadDataException();

        User sender = userRepositoryJpa.getById(newMessage.getUserId());
        Room room = userOpensRepository.getRoom(newMessage.getRoomId());

        room.checkIsUserInRoom(newMessage.getUserId());
    }
}
