package com.megamainmeeting.push;

import com.megamainmeeting.domain.UserNotifier;
import com.megamainmeeting.domain.block.NewRoomBlock;
import com.megamainmeeting.domain.match.RoomPreparing;
import com.megamainmeeting.domain.open.OpenRequest;
import com.megamainmeeting.domain.open.RoomBlockingStatus;
import com.megamainmeeting.entity.chat.ChatMessage;

import java.util.List;

public class UserNotifierList implements UserNotifier {

    private final List<UserNotifier> userNotifiers;

    public UserNotifierList(List<UserNotifier> userNotifiers) {
        this.userNotifiers = userNotifiers;
    }

    @Override
    public void notifyRoomReady(com.megamainmeeting.entity.room.Room room) {
        userNotifiers.forEach((userNotifier ->
                userNotifier.notifyRoomReady(room)
        ));
    }

    @Override
    public void notifyUsersRefuse(RoomPreparing preparing) {
        userNotifiers.forEach((userNotifier ->
                userNotifier.notifyUsersRefuse(preparing)
        ));
    }

    @Override
    public void notifyUserRefuseChat(long userId) {
        userNotifiers.forEach((userNotifier ->
                userNotifier.notifyUserRefuseChat(userId)
        ));
    }

    @Override
    public void notifyMatch(RoomPreparing preparing) {
        userNotifiers.forEach((userNotifier ->
                userNotifier.notifyMatch(preparing)
        ));
    }

    @Override
    public void notifyChatMessageUpdated(ChatMessage message) {
        userNotifiers.forEach((userNotifier ->
                userNotifier.notifyChatMessageUpdated(message)
        ));
    }

    @Override
    public void notifyUserShouldOpens(long userId, OpenRequest openRequest) {
        userNotifiers.forEach((userNotifier ->
                userNotifier.notifyUserShouldOpens(userId, openRequest)
        ));
    }

    @Override
    public void notifyUserOpens(long userId, RoomBlockingStatus roomBlockingStatus) {
        userNotifiers.forEach((userNotifier ->
                userNotifier.notifyUserOpens(userId, roomBlockingStatus)
        ));
    }

    @Override
    public void notifyRoomBlocked(long userId, NewRoomBlock roomBlockedChatNotification) {
        userNotifiers.forEach((userNotifier ->
                userNotifier.notifyRoomBlocked(userId, roomBlockedChatNotification)
        ));
    }
}
