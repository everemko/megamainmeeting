package db.repository;

import db.ChatMessageRepositoryJpa;
import db.dto.ChatMessageDb;
import db.dto.RoomDb;
import db.dto.UserDb;
import domain.ChatMessageRepository;
import domain.RoomRepository;
import domain.entity.chat.ChatMessage;
import domain.entity.chat.NewChatMessage;
import domain.entity.chat.Room;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class ChatMessageRepositoryImpl implements ChatMessageRepository {

    private final ChatMessageRepositoryJpa chatMessageRepositoryJpa;
    private final RoomRepository roomRepository;
    private final Map<Long, ChatMessage> messages = new HashMap<>();
    private long id = 0;


    @Override
    public ChatMessage save(NewChatMessage message) {

//        UserDb userDb = new UserDb();
//        userDb.setId(message.getUserId());
//        RoomDb roomDb = new RoomDb();
//        roomDb.setId(message.getRoomId());
//        ChatMessageDb chatMessageDb = new ChatMessageDb();
//        chatMessageDb.setUser(userDb);
//        chatMessageDb.setRoom(roomDb);
//        ChatMessageDb savedMessage = chatMessageRepositoryJpa.save(chatMessageDb);
//        return savedMessage.toDomain();
        Room room = roomRepository.get(message.getRoomId());
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setId(id);
        chatMessage.setUserId(message.getUserId());
        chatMessage.setRoom(room);
        chatMessage.setText(message.getText());
        messages.put(message.getRoomId(), chatMessage);
        id++;
        return chatMessage;
    }
}
