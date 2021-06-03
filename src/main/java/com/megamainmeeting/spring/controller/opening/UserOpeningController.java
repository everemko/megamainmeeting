package com.megamainmeeting.spring.controller.opening;

import com.megamainmeeting.domain.error.OpenRequestNotFoundException;
import com.megamainmeeting.domain.error.RoomNotFoundException;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.domain.error.UserNotInRoomException;
import com.megamainmeeting.domain.open.*;
import com.megamainmeeting.spring.base.BaseResponse;
import com.megamainmeeting.spring.base.SuccessResponse;
import com.megamainmeeting.spring.controller.room.RoomBlock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
public class UserOpeningController {

    @Autowired
    UserOpeningCheck userOpeningCheck;
    @Autowired
    UserOpensRepository userOpensRepository;

    @PostMapping("user/open")
    public BaseResponse<?> userOpen(@RequestAttribute("UserId") long userId,
                                    @RequestBody UserOpens userOpens) throws RoomNotFoundException, UserNotInRoomException,
            UserNotFoundException, OpenRequestNotFoundException {
        userOpens.setUserId(userId);
        userOpeningCheck.addUserOpen(userOpens);
        return SuccessResponse.getSimpleSuccessResponse();
    }

    @PostMapping("user/open/available")
    public BaseResponse<Set<UserOpenType>> getOpensAvailable(@RequestAttribute("UserId") long userId,
                                                             @RequestBody long roomId) throws RoomNotFoundException,
            UserNotInRoomException, OpenRequestNotFoundException {
        Room room = userOpensRepository.getRoom(roomId);
        User user = room.getUser(userId);
        return SuccessResponse.getSuccessInstance(user.getAvailable());
    }

    @PostMapping("room/blocking/status")
    public BaseResponse<RoomBlockingStatus> getRoomBlockingStatus(@RequestAttribute("UserId") long userId,
                                                                  @RequestBody long roomId) throws RoomNotFoundException,
            UserNotInRoomException, OpenRequestNotFoundException {
        RoomBlockingStatus roomBlockingStatus = getBlockingStatus(userId, roomId);
        return SuccessResponse.getSuccessInstance(roomBlockingStatus);
    }

    @PostMapping("room/blocking/status/list")
    public BaseResponse<List<RoomBlockingStatus>> getRoomBlockingStatus(@RequestAttribute("UserId") long userId,
                                                                  @RequestBody List<Long> roomIdList)
            throws RoomNotFoundException, UserNotInRoomException, OpenRequestNotFoundException{
        List<RoomBlockingStatus> list = new ArrayList<>();
        for(Long roomId: roomIdList){
            RoomBlockingStatus roomBlockingStatus = getBlockingStatus(userId, roomId);
            list.add(roomBlockingStatus);
        }
        return SuccessResponse.getSuccessInstance(list);
    }

    private RoomBlockingStatus getBlockingStatus(long userId, long roomId) throws RoomNotFoundException, UserNotInRoomException, OpenRequestNotFoundException{
        Room room = userOpensRepository.getRoom(roomId);
        room.checkIsUserInRoom(userId);
        return RoomBlockingStatus.getInstance(room);
    }
}
