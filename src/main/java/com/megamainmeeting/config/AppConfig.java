package com.megamainmeeting.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.megamainmeeting.db.ChatMessageRepositoryJpa;
import com.megamainmeeting.db.RoomRepositoryJpa;
import com.megamainmeeting.db.SessionRepositoryJpa;
import com.megamainmeeting.db.UserRepositoryJpa;
import com.megamainmeeting.db.mapper.ChatMessageDbMapper;
import com.megamainmeeting.db.repository.*;
import com.megamainmeeting.domain.*;
import com.megamainmeeting.domain.match.UserChatMatcher;
import com.megamainmeeting.domain.match.UserChatPreparer;
import com.megamainmeeting.domain.open.UserOpeningCheck;
import com.megamainmeeting.interactor.*;
import com.megamainmeeting.spring.SocketSessions;
import com.megamainmeeting.spring.UserSocketClientManager;
import com.megamainmeeting.spring.socket.ChatWebSocketHandler;
import com.megamainmeeting.spring.socket.UserSocketManagerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Configuration
public class AppConfig {

    @Bean
    public Logger logger() {
        return LoggerFactory.getLogger("application");
    }

    @Bean
    public ObjectMapper provideObjectMapper() {
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME));
        timeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME));
        return new ObjectMapper().registerModule(timeModule);
    }

    @Bean
    public RegistrationRepository provideRegistrationRepository(UserRepositoryJpa userRepositoryJpa,
                                                                SessionRepositoryJpa sessionRepositoryJpa) {
        return new RegistrationRepositoryImpl(userRepositoryJpa, sessionRepositoryJpa);
    }

    @Bean
    public UserChatCandidateQueue provideUserChatCandidateQueue(
            RoomRepository roomRepository
    ) {
        return new UserChatCandidateQueueImpl(roomRepository);
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
    UserChatMatcher provideUserChatMatcher(
            UserChatCandidateQueue userChatCandidateQueue,
            UserChatPreparer userChatPreparer
            ) {
        return new UserChatMatcher(userChatCandidateQueue,
                userChatPreparer
        );
    }

    @Bean
    UserChatPreparer provideUserChatPreparer(UserChatCandidateQueue userMatchQueue,
                                             RoomRepository roomRepository,
                                             UserNotifier userMatchNorifier,
                                             UserRepository userRepository,
                                             RoomPreparingRepository roomPreparingRepository) {
        return new UserChatPreparer(
                roomPreparingRepository,
                userMatchNorifier,
                roomRepository,
                userRepository,
                userMatchQueue,
                new ScheduledThreadPoolExecutor(1)
        );
    }

    @Bean
    public UserChatCandidateInteractor provideUserChatCandidateInteractor(UserChatMatcher userChatMatcher,
                                                                          UserChatCandidateQueue userChatCandidateQueue,
                                                                          UserRepository userRepository,
                                                                          UserChatPreparer userChatPreparer,
                                                                          RoomRepository roomRepository) {
        return new UserChatCandidateInteractor(
                userChatCandidateQueue,
                userRepository,
                roomRepository,
                userChatMatcher,
                userChatPreparer
        );
    }

    @Bean
    ChatMessageInteractor provideChatMessageInteractor(MessageChatManager messageChatManager,
                                                       ChatMessageRepository chatMessageRepository,
                                                       RoomRepositoryJpa roomRepositoryJpa,
                                                       UserOpeningCheck userOpeningCheck,
                                                       ChatMessageDbMapper chatMessageDbMapper,
                                                       UserMessagePushService messagePushService) {
        return new ChatMessageInteractor(messageChatManager,
                chatMessageRepository,
                roomRepositoryJpa,
                userOpeningCheck,
                messagePushService,
                chatMessageDbMapper);
    }

    @Bean
    RoomInteractor roomInteractor(RoomRepositoryJpa roomRepository, UserNotifier userNotifier) {
        return new RoomInteractor(roomRepository, userNotifier);
    }

    @Bean
    UserSocketClientManager userSocketClientManager(ObjectMapper objectMapper,
                                                    Logger logger,
                                                    SocketSessions socketSessions) {
        return new UserSocketManagerImpl(objectMapper, logger, socketSessions);
    }

    @Bean(name = "loggedChatWebSocketHandler")
    WebSocketHandler provideWebSocketHandler(ChatWebSocketHandler chatWebSocketHandler) {
        return chatWebSocketHandler;
    }

}
