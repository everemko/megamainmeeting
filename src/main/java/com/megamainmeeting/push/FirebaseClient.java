package com.megamainmeeting.push;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.util.IOUtils;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import com.megamainmeeting.interactor.UserMessagePushService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class FirebaseClient implements UserMessagePushService {

    private static final String MESSAGE_DATA = "message";
    private static final String TITLE_DATA = "title";


    private final Logger logger;
    private UserPushTokenRepository userPushTokenRepository;

    public FirebaseClient(Logger logger, UserPushTokenRepository userPushTokenRepository) {
        this.logger = logger;
        this.userPushTokenRepository = userPushTokenRepository;
        try (InputStream stream = this.getClass()
                .getResourceAsStream("/megamainmeeting-firebase-adminsdk-s8fes-dd27d29c92.json")) {

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(stream))
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (IOException exception) {
            logger.error(getClass().getSimpleName(), exception);
        } catch (IllegalStateException exception){
            logger.error(getClass().getSimpleName(), exception);
        }
    }

    @Override
    public void send(Set<Long> users, String message) {
        Collection<String> tokens = users.stream()
                .map(it -> {
                    try {
                        return Optional.ofNullable(userPushTokenRepository.getToken(it));
                    } catch (UserPushTokenNotFound e){
                        return Optional.<String>empty();
                    }
                })
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
        if(tokens.isEmpty()) return;
        Notification notification = Notification.builder()
                .setBody(message)
                .setTitle("Title")
                .build();
        MulticastMessage multicastMessage = MulticastMessage.builder()
                .setNotification(notification)
                .addAllTokens(tokens)
                .build();
        try {
            BatchResponse response = FirebaseMessaging.getInstance()
                    .sendMulticast(multicastMessage);
        } catch (FirebaseMessagingException exception) {
            logger.error(getClass().getSimpleName(), exception);
        }
    }
}
