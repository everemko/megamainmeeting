package com.megamainmeeting.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.megamainmeeting.TestClientManager;
import com.megamainmeeting.db.RoomRepositoryJpa;
import com.megamainmeeting.db.repository.*;
import com.megamainmeeting.domain.*;
import com.megamainmeeting.interactor.ChatMessageInteractor;
import com.megamainmeeting.interactor.UserChatCandidateInteractor;
import com.megamainmeeting.domain.match.UserChatMatcher;
import com.megamainmeeting.domain.match.UserChatPreparer;
import com.megamainmeeting.spring.UserSocketClientManager;
import com.megamainmeeting.spring.socket.ChatWebSocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.handler.LoggingWebSocketHandlerDecorator;

import java.util.concurrent.ScheduledThreadPoolExecutor;

@TestConfiguration
public class AppConfigTest {

    @Bean
    public Logger logger() {
        return LoggerFactory.getLogger("application");
    }

    @Bean
    public ObjectMapper provideObjectMapper(){
        return new ObjectMapper();
    }

    @Bean
    public UserChatCandidateQueue provideUserChatCandidateQueue(
            RoomRepository roomRepository
    ) {
        return new UserChatCandidateQueueImpl(roomRepository);
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
                                                                          UserChatPreparer userChatPreparer) {
        return new UserChatCandidateInteractor(
                userChatCandidateQueue,
                userRepository,
                userChatMatcher,
                userChatPreparer
        );
    }

    @Bean
    ChatMessageInteractor provideChatMessageInteractor(MessageChatManager messageChatManager,
                                                       ChatMessageRepository chatMessageRepository,
                                                       RoomRepositoryJpa roomRepositoryJpa) {
        return new ChatMessageInteractor(messageChatManager, chatMessageRepository, roomRepositoryJpa);
    }

    @Bean
    UserSocketClientManager userSocketClientManager(){
        return new TestClientManager();
    }

    @Bean(name = "loggedChatWebSocketHandler")
    WebSocketHandler provideWebSocketHandler(){
        return new LoggingWebSocketHandlerDecorator(new ChatWebSocketHandler());
    }
}
