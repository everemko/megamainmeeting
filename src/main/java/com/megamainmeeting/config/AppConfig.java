package com.megamainmeeting.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.megamainmeeting.auth.AuthenticationInteractorImpl;
import com.megamainmeeting.auth.AuthenticationSessionsRepository;
import com.megamainmeeting.auth.AuthenticationUserRepository;
import com.megamainmeeting.boxhosting.BoxFileHosting;
import com.megamainmeeting.boxhosting.BoxHostingUserAvatarRepository;
import com.megamainmeeting.boxhosting.BoxImageRepository;
import com.megamainmeeting.domain.*;
import com.megamainmeeting.domain.block.RoomBlockRepository;
import com.megamainmeeting.domain.block.RoomDeleteRepository;
import com.megamainmeeting.domain.match.UserChatMatcher;
import com.megamainmeeting.domain.match.UserChatPreparer;
import com.megamainmeeting.domain.messaging.ChatMessageInteractor;
import com.megamainmeeting.domain.messaging.UserMessagePushService;
import com.megamainmeeting.domain.open.UserOpeningCheck;
import com.megamainmeeting.domain.interactor.*;
import com.megamainmeeting.domain.block.RoomInteractor;
import com.megamainmeeting.domain.useravatar.UpdateAvatarInteractor;
import com.megamainmeeting.domain.useravatar.UserProfileAvatarRepository;
import com.megamainmeeting.push.FirebaseClient;
import com.megamainmeeting.push.UserNotifierList;
import com.megamainmeeting.database.repository.*;
import com.megamainmeeting.spring.socket.handler.ChatWebSocketHandler;
import com.megamainmeeting.spring.socket.UserMatchNotifierImpl;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.WebSocketHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Configuration
@EnableScheduling
public class AppConfig {

    @Bean
    public Logger logger() {
        return LoggerFactory.getLogger("application");
    }

    @Bean
    public org.apache.logging.log4j.Logger provideLog(){
        return LogManager.getLogger();
    }

    @Bean
    public java.util.logging.Logger provideLogger(){
        return java.util.logging.Logger.getLogger("application");
    }

    @Bean
    public ObjectMapper provideObjectMapper() {
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME));
        timeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME));
        return new ObjectMapper().registerModule(timeModule);
    }


    @Bean
    UserChatMatcher provideUserChatMatcher() {
        return new UserChatMatcher();
    }

    @Bean
    @Primary
    UserNotifier provideUserNotifier(
            FirebaseClient firebaseClient,
            UserMatchNotifierImpl matchNotifier
    ) {
        List<UserNotifier> notifiers = new ArrayList<>();
        notifiers.add(matchNotifier);
        notifiers.add(firebaseClient);
        return new UserNotifierList(notifiers);
    }

    @Bean
    UserChatPreparer provideUserChatPreparer() {
        return new UserChatPreparer(
                new ScheduledThreadPoolExecutor(1)
        );
    }

    @Bean
    public UserChatCandidateInteractor provideUserChatCandidateInteractor() {
        return new UserChatCandidateInteractor();
    }

    @Bean
    ChatMessageInteractor provideChatMessageInteractor() {
        return new ChatMessageInteractor();
    }

    @Bean
    RoomInteractor roomInteractor() {
        return new RoomInteractor();
    }

    @Bean(name = "loggedChatWebSocketHandler")
    WebSocketHandler provideWebSocketHandler(ChatWebSocketHandler chatWebSocketHandler) {
        return chatWebSocketHandler;
    }

    @Bean
    ImageRepository provideImageRepository(){
        return new BoxImageRepository();
    }

    @Bean
    BoxFileHosting boxFileHosting(){
        return new BoxFileHosting();
    }

    @Bean
    AuthenticationInteractorImpl provideAuthenticationInteractor(
    ) {
        return new AuthenticationInteractorImpl();
    }

    @Bean
    UserProfileAvatarRepository provideUserProfileAvatarRepository(){
        return new BoxHostingUserAvatarRepository();
    }

    @Bean
    UpdateAvatarInteractor provideUpdateAvatarInteractor(){
        return new UpdateAvatarInteractor();
    }
}
