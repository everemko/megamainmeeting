package com.megamainmeeting.interactor;

import java.util.Set;

public interface UserMessagePushService {

    void send(Set<Long> users, String message);
}
