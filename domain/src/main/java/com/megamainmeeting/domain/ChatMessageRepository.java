package com.megamainmeeting.domain;

import com.megamainmeeting.domain.error.ChatMessageNotFoundException;
import com.megamainmeeting.domain.error.RoomNotFoundException;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.entity.chat.ChatMessage;
import com.megamainmeeting.entity.chat.NewChatMessage;

import java.io.IOException;

public interface ChatMessageRepository {

    public ChatMessage save(NewChatMessage message) throws RoomNotFoundException, UserNotFoundException, IOException;

    ChatMessage get(long messageId) throws ChatMessageNotFoundException;

    void update(ChatMessage message) throws ChatMessageNotFoundException;
}
