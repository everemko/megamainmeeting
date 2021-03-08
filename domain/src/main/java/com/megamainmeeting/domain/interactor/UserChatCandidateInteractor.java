package com.megamainmeeting.domain.interactor;

import com.megamainmeeting.domain.*;
import com.megamainmeeting.domain.error.UserAlreadyCandidateException;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.domain.error.UserNotMatchException;
import com.megamainmeeting.entity.chat.Room;
import com.megamainmeeting.entity.chat.RoomPreparing;
import com.megamainmeeting.entity.user.User;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@AllArgsConstructor
public class UserChatCandidateInteractor {

    private static final long DELAY = 3000;

    private final UserChatCandidateQueue userMatchQueue;
    private final RoomRepository roomRepository;
    private final UserNotifier matchNotifier;
    private final UserRepository userRepository;
    private final RoomPreparingRepository roomPreparingRepository;
    private final ScheduledExecutorService scheduledExecutorService;
    private final Map<RoomPreparing, OnceRunnable> returnToMatchQueueMap = new HashMap<>();

    public void add(long userId) throws UserNotFoundException, UserAlreadyCandidateException{
        User user = userRepository.get(userId);
        if(userMatchQueue.isExist(user)) throw new UserAlreadyCandidateException();
        User userMatched = userMatchQueue.findMatch(user);
        if (userMatched == null) {
            userMatchQueue.add(user);
        } else {
            RoomPreparing roomPreparing = new RoomPreparing();
            roomPreparing.addUser(user.getId());
            roomPreparing.addUser(userMatched.getId());
            roomPreparingRepository.add(roomPreparing);
            scheduleReturnToQueue(roomPreparing);
            matchNotifier.notifyMatch(roomPreparing);
        }
    }

    private void scheduleReturnToQueue(RoomPreparing roomPreparing){
        OnceRunnable runnable = new OnceRunnable(() -> {
            roomPreparingRepository.remove(roomPreparing);
            if(roomPreparing.isAllReady()){
                Room room = roomRepository.create(roomPreparing.getUsers());
                matchNotifier.notifyRoomReady(room);
            } else{
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

    public void setUserReady(long userId) throws UserNotMatchException{
        RoomPreparing roomPreparing = roomPreparingRepository.get(userId);
        roomPreparing.setReady(userId);
        if(roomPreparing.isAllReady() ){
            returnToMatchQueueMap.get(roomPreparing).run();
        }
    }

    public void setUserNotReady(long userId) throws UserNotMatchException, UserNotFoundException {
        RoomPreparing roomPreparing = roomPreparingRepository.get(userId);
        returnToMatchQueueMap.get(roomPreparing).run();
    }

    public void remove(long userId) throws UserNotFoundException{
        if(!userRepository.isExist(userId)) throw new UserNotFoundException();
        userMatchQueue.remove(userId);
    }

}
