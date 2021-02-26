package domain.interactor;

import domain.UserMatchNotifier;
import domain.RoomRepository;
import domain.UserChatCandidateQueue;
import domain.UserRepository;
import domain.entity.chat.Room;
import domain.entity.user.User;
import domain.error.UserNotChatMatchException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@AllArgsConstructor
public class UserChatCandidateInteractor {

    private final UserChatCandidateQueue userMatchQueue;
    private final RoomRepository roomRepository;
    private final UserMatchNotifier matchNotifier;
    private final UserRepository userRepository;
    private final Map<Long, RoomPreparing> roomPreparings = new HashMap<>();

    public void add(long userId) throws NoSuchElementException {
        User user = userRepository.get(userId);
        User user2 = userMatchQueue.getMatch(user);
        if (user2 == null) {
            userMatchQueue.add(user);
        } else {
            RoomPreparing roomPreparing = new RoomPreparing(user.getId(), user2.getId());
            roomPreparings.put(roomPreparing.user1, roomPreparing);
            roomPreparings.put(roomPreparing.user2, roomPreparing);
            matchNotifier.notifyMatch(roomPreparing);
        }
    }


    public void setUserReady(long userId) throws UserNotChatMatchException{
        RoomPreparing roomPreparing = roomPreparings.get(userId);
        if(roomPreparing == null) throw new UserNotChatMatchException();
        roomPreparing.setReady(userId);
        if(roomPreparing.isAllReady()){
            Room room = roomRepository.create(roomPreparing.user1, roomPreparing.user2);
            matchNotifier.notifyRoomReady(room);
        }
    }

    public void setUserNotReady(long userId){
        RoomPreparing roomPreparing = roomPreparings.get(userId);
        if(roomPreparing == null) throw new NoSuchElementException();
        matchNotifier.notifyUsersRefuse(roomPreparing);
        roomPreparing.getUsers().forEach(roomPreparings::remove);
    }


    public class RoomPreparing{
        @Getter
        private long user1 = -1;
        @Getter
        private long user2 = -1;
        private boolean user1Ready = false;
        private boolean user2Ready = false;

        public RoomPreparing(long user1, long user2){
            this.user1 = user1;
            this.user2 = user2;
        }

        void setReady(long user){
            if(user == user1) user1Ready = true;
            if(user == user2) user2Ready = true;
        }

        public Set<Long> getUsers(){
            return new HashSet<>(Arrays.asList(user1, user2));
        }

        boolean isAllReady(){
            return user1Ready && user2Ready;
        }
    }
}
