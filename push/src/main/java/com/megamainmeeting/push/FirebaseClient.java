package com.megamainmeeting.push;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import com.megamainmeeting.domain.messaging.UserMessagePushService;
import com.megamainmeeting.entity.chat.ChatMessage;
import com.megamainmeeting.entity.user.User;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Set;

public class FirebaseClient implements UserMessagePushService {

    private static final String FIREBASE_MESSAGING_NULL = "Firebase messaging is null";
    private static final String CLIENT_RESOURCE = "/megamainmeeting-firebase-adminsdk-s8fes-dd27d29c92.json";
    private final String TAG = getClass().getSimpleName();
    private static final String DEFAULT_CHANNEL = "DEFAULT_CHANNEL";
    private static final String DEEPLINK = "deeplink";
    private static final String DEEPLINK_SCHEME = "megamainmeeting";
    private static final String DEEPLINK_HOST = "megamainmeeting";
    private static final String DEEPLINK_DEFAULT = DEEPLINK_SCHEME + "://" + DEEPLINK_HOST;
    private static final String DEEPLINK_MESSAGE = DEEPLINK_DEFAULT + "/message";
    private static final String DEEPLINK_OPEN_REQUEST = DEEPLINK_DEFAULT + "/openrequest";

    private final Logger logger;
    private final UserPushTokenRepository userPushTokenRepository;

    private FirebaseMessaging firebaseMessaging;

    public FirebaseClient(Logger logger, UserPushTokenRepository userPushTokenRepository) {
        this.logger = logger;
        this.userPushTokenRepository = userPushTokenRepository;
        try (InputStream stream = this.getClass().getResourceAsStream(CLIENT_RESOURCE)) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(stream))
                    .build();
            FirebaseApp.initializeApp(options);
            this.firebaseMessaging = FirebaseMessaging.getInstance();
        } catch (IOException | IllegalStateException exception) {
            logger.error(getClass().getSimpleName(), exception);
        }
    }

    @Override
    public void sendMessage(ChatMessage message, User sender) {
        if (message.isHasImage() && message.isHasTextMessage()) {
            sendMessageWithImage(message.getUsersWithoutSender(), message.getMessage(), message.getImageUrl(), sender.getName());
            return;
        }

        if (message.isHasTextMessage()) {
            sendMessage(message.getUsersWithoutSender(), message.getMessage(), sender.getName());
            return;
        }

        if (message.isHasImage()) {
            sendImage(message.getUsersWithoutSender(), message.getImageUrl(), sender.getName());
            return;
        }
    }

    @Override
    public void sendImage(Set<Long> users, String imageUrl, String senderName) {
        Collection<String> tokens = userPushTokenRepository.getTokens(users);
        if (tokens.isEmpty()) return;
        AndroidNotification androidNotification = AndroidNotification.builder()
                .setChannelId(DEFAULT_CHANNEL)
                .setTitle(senderName)
                .setImage(imageUrl)
                .build();
        MulticastMessage multicastMessage = prepareMessageMulticastMessage(androidNotification, tokens);
        sendMulticast(multicastMessage);
    }

    @Override
    public void sendMessageWithImage(Set<Long> users, String message, String imageUrl, String senderName) {
        Collection<String> tokens = userPushTokenRepository.getTokens(users);
        if (tokens.isEmpty()) return;
        AndroidNotification androidNotification = AndroidNotification.builder()
                .setChannelId(DEFAULT_CHANNEL)
                .setBody(message)
                .setTitle(senderName)
                .setIcon("ic_logo")
                .setImage(imageUrl)
                .setPriority(AndroidNotification.Priority.HIGH)
                .build();
        MulticastMessage multicastMessage = prepareMessageMulticastMessage(androidNotification, tokens);
        sendMulticast(multicastMessage);
    }

    @Override
    public void sendMessage(Set<Long> users, String message, String senderName) {
        Collection<String> tokens = userPushTokenRepository.getTokens(users);
        if (tokens.isEmpty()) return;
        AndroidNotification androidNotification = AndroidNotification.builder()
                .setChannelId(DEFAULT_CHANNEL)
                .setTitle(senderName)
                .setBody(message)
                .build();
        MulticastMessage multicastMessage = prepareMessageMulticastMessage(androidNotification, tokens);
        sendMulticast(multicastMessage);
    }

    @Override
    public void sendOpeningRequest(Set<Long> users, String senderName) {
        Collection<String> tokens = userPushTokenRepository.getTokens(users);
        if (tokens.isEmpty()) return;
        AndroidNotification androidNotification = AndroidNotification.builder()
                .setChannelId(DEFAULT_CHANNEL)
                .setTitle(senderName)
                .build();
        AndroidConfig androidConfig = AndroidConfig.builder()
                .setNotification(androidNotification)
                .build();
        MulticastMessage multicastMessage = MulticastMessage.builder()
                .setAndroidConfig(androidConfig)
                .addAllTokens(tokens)
                .putData(DEEPLINK, DEEPLINK_OPEN_REQUEST)
                .build();
        sendMulticast(multicastMessage);
    }

    private MulticastMessage prepareMessageMulticastMessage(AndroidNotification androidNotification, Collection<String> tokens) {
        AndroidConfig androidConfig = AndroidConfig.builder()
                .setNotification(androidNotification)
                .build();
        return MulticastMessage.builder()
                .addAllTokens(tokens)
                .setAndroidConfig(androidConfig)
                .putData(DEEPLINK, DEEPLINK_MESSAGE)
                .build();
    }

    private void sendMulticast(MulticastMessage multicastMessage) {
        try {
            firebaseMessaging.sendMulticast(multicastMessage);
        } catch (FirebaseMessagingException exception) {
            logger.error(TAG, exception);
        } catch (NullPointerException exception) {
            logger.error(TAG, FIREBASE_MESSAGING_NULL);
        }
    }
}
