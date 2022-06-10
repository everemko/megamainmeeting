package com.megamainmeeting.push;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.megamainmeeting.domain.UserNotifier;
import com.megamainmeeting.domain.block.RoomBlocked;
import com.megamainmeeting.domain.match.RoomPreparing;
import com.megamainmeeting.domain.messaging.UserMessagePushService;
import com.megamainmeeting.domain.open.OpenRequest;
import com.megamainmeeting.domain.open.RoomBlockingStatus;
import com.megamainmeeting.entity.chat.ChatMessage;
import com.megamainmeeting.entity.room.Room;
import com.megamainmeeting.entity.user.User;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Set;

public class FirebaseClient implements UserMessagePushService, UserNotifier {

    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    private static final String FIREBASE_MESSAGING_NULL = "Firebase messaging is null";
    private static final String CLIENT_RESOURCE = "/megamainmeeting-firebase-adminsdk-s8fes-dd27d29c92.json";
    private final String TAG = getClass().getSimpleName();

    private static final String DATA_MESSAGE = "message";
    private static final String DATA_MESSAGE_SENDER = "message_sender";
    private static final String DATA_OPEN_REQUEST = "open_request";

    private final Logger logger;
    private final UserPushTokenRepository userPushTokenRepository;
    private final Gson gson = new GsonBuilder()
            .setDateFormat(DATE_FORMAT)
            .create();

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
        Collection<String> tokens = userPushTokenRepository.getTokens(message.getUsersWithoutSender());
        String messageJson = gson.toJson(message);
        String senderJson = gson.toJson(sender);
        MulticastMessage multicastMessage = MulticastMessage.builder()
                .addAllTokens(tokens)
                .putData(DATA_MESSAGE, messageJson)
                .putData(DATA_MESSAGE_SENDER, senderJson)
                .build();
        sendMulticast(multicastMessage);
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

    @Override
    public void notifyRoomReady(Room room) {

    }

    @Override
    public void notifyUsersRefuse(RoomPreparing preparing) {

    }

    @Override
    public void notifyUserRefuseChat(long userId) {

    }

    @Override
    public void notifyMatch(RoomPreparing preparing) {

    }

    @Override
    public void notifyChatMessageUpdated(ChatMessage message) {

    }

    @Override
    public void notifyUserShouldOpens(long userId, OpenRequest openRequest) {
        try {
            Collection<String> tokens = userPushTokenRepository.getToken(userId);
            String openRequestJson = gson.toJson(openRequest);
            MulticastMessage multicastMessage = MulticastMessage.builder()
                    .addAllTokens(tokens)
                    .putData(DATA_OPEN_REQUEST, openRequestJson)
                    .build();
            sendMulticast(multicastMessage);
        } catch (UserPushTokenNotFound userPushTokenNotFound) {

        }
    }

    @Override
    public void notifyUserOpens(long userId, RoomBlockingStatus roomBlockingStatus) {

    }

    @Override
    public void notifyRoomBlocked(long userId, RoomBlocked roomBlockedChatNotification) {

    }
}
