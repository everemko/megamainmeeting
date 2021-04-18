package com.megamainmeeting.interactor;

import com.megamainmeeting.domain.*;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.domain.error.UserNotMatchException;
import com.megamainmeeting.entity.room.Room;
import com.megamainmeeting.entity.chat.RoomPreparing;
import com.megamainmeeting.entity.user.User;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
public class UserChatPreparer {

    private static final long DELAY = 3000;

    private final RoomPreparingRepository roomPreparingRepository;
    private final UserNotifier matchNotifier;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final UserChatCandidateQueue userMatchQueue;

    private final ScheduledExecutorService scheduledExecutorService;
    private final Map<RoomPreparing, OnceRunnable> returnToMatchQueueMap = new HashMap<>();

    public void prepare(Set<User> users) {
        RoomPreparing roomPreparing = new RoomPreparing();
        users.forEach((user) -> {
            roomPreparing.addUser(user.getId());
            roomPreparing.addUser(user.getId());
        });
        matchNotifier.notifyMatch(roomPreparing);
        roomPreparingRepository.add(roomPreparing);
        scheduleReturnToQueue(roomPreparing);
    }

    public void setReady(User user) throws UserNotMatchException{
        RoomPreparing roomPreparing = roomPreparingRepository.get(user.getId());
        roomPreparing.setReady(user.getId());
        if(roomPreparing.isAllReady() ){
            returnToMatchQueueMap.get(roomPreparing).run();
        }
    }

    public void setNotReady(User user) throws UserNotMatchException{
        RoomPreparing roomPreparing = roomPreparingRepository.get(user.getId());
        returnToMatchQueueMap.get(roomPreparing).run();
    }

    private void scheduleReturnToQueue(RoomPreparing roomPreparing) {
        OnceRunnable runnable = new OnceRunnable(() -> {
            roomPreparingRepository.remove(roomPreparing);
            if (roomPreparing.isAllReady()) {
                Room room = roomRepository.create(roomPreparing.getUsers());
                matchNotifier.notifyRoomReady(room);
            } else {
                returnToQueue(roomPreparing.getReadyUsers());
                notifyNotReady(roomPreparing.getNotReadyUsers());
            }
        });
        scheduledExecutorService.schedule(runnable, DELAY, TimeUnit.MILLISECONDS);
        returnToMatchQueueMap.put(roomPreparing, runnable);
    }

    private void returnToQueue(Set<Long> users){
        for(long id: users){
            try {
                User user = userRepository.get(id);
                userMatchQueue.add(user);
            } catch (UserNotFoundException exception){

            }
        }
    }

    private void notifyNotReady(Set<Long> users){
        for(long id: users){
            matchNotifier.notifyUserRefuseChat(id);
        }
    }
}
