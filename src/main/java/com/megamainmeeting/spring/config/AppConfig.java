package com.megamainmeeting.spring.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.megamainmeeting.db.ChatMessageRepositoryJpa;
import com.megamainmeeting.db.RoomRepositoryJpa;
import com.megamainmeeting.db.SessionRepositoryJpa;
import com.megamainmeeting.db.UserRepositoryJpa;
import com.megamainmeeting.db.repository.*;
import com.megamainmeeting.domain.*;
import com.megamainmeeting.domain.interactor.ChatMessageInteractor;
import com.megamainmeeting.domain.interactor.RoomInteractor;
import com.megamainmeeting.domain.interactor.UserChatCandidateInteractor;
import com.megamainmeeting.spring.SocketSessions;
import com.megamainmeeting.spring.UserSocketClientManager;
import com.megamainmeeting.spring.socket.ChatWebSocketHandler;
import com.megamainmeeting.spring.socket.UserSocketManagerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.handler.LoggingWebSocketHandlerDecorator;

@Configuration
public class AppConfig {

    @Bean
    public Logger logger() {
        return LoggerFactory.getLogger("application");
    }

    @Bean
    public ObjectMapper provideObjectMapper(){
        return new ObjectMapper();
    }

    @Bean
    public RegistrationRepository provideRegistrationRepository(UserRepositoryJpa userRepositoryJpa,
                                                                SessionRepositoryJpa sessionRepositoryJpa) {
        return new RegistrationRepositoryImpl(userRepositoryJpa, sessionRepositoryJpa);
    }

    @Bean
    public UserChatCandidateQueue provideUserChatCandidateQueue() {
        return new UserChatCandidateQueueImpl();
    }

    @Bean
    public RoomRepository provideRoomRepository(RoomRepositoryJpa roomRepositoryJpa) {
        return new RoomRepositoryImpl(roomRepositoryJpa);
//        return new RoomRepositoryMemory();
    }

    @Bean
    public UserRepository provideUserRepository(UserRepositoryJpa userRepositoryJpa) {
        return new UserRepositoryImpl(userRepositoryJpa);
    }

    @Bean
    ChatMessageRepository provideChatMessageRepository(ChatMessageRepositoryJpa chatMessageRepositoryJpa,
                                                       RoomRepository roomRepository,
                                                       UserRepository userRepository) {
        return new ChatMessageRepositoryImpl(chatMessageRepositoryJpa);
    }

    @Bean
    public UserChatCandidateInteractor provideUserChatCandidateInteractor(UserChatCandidateQueue userMatchQueue,
                                                                          RoomRepository roomRepository,
                                                                          UserMatchNotifier userMatchNorifier,
                                                                          UserRepository userRepository,
                                                                          RoomPreparingRepository roomPreparingRepository) {
        return new UserChatCandidateInteractor(
                userMatchQueue,
                roomRepository,
                userMatchNorifier,
                userRepository,
                roomPreparingRepository);
    }

    @Bean
    ChatMessageInteractor provideChatMessageInteractor(MessageChatManager messageChatManager,
                                                       ChatMessageRepository chatMessageRepository) {
        return new ChatMessageInteractor(messageChatManager, chatMessageRepository);
    }

    @Bean
    RoomInteractor roomInteractor(RoomRepository roomRepository, SessionRepository sessionRepository){
        return new RoomInteractor(roomRepository, sessionRepository);
    }

    @Bean
    UserSocketClientManager userSocketClientManager(ObjectMapper objectMapper,
                                                    Logger logger,
                                                    SocketSessions socketSessions){
        return new UserSocketManagerImpl(objectMapper, logger, socketSessions);
    }

    @Bean(name = "loggedChatWebSocketHandler")
    WebSocketHandler provideWebSocketHandler(ChatWebSocketHandler chatWebSocketHandler){
        return chatWebSocketHandler;
    }
}
