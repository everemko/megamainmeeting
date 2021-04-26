package com.megamainmeeting.spring.controller.opening;

import com.megamainmeeting.domain.error.RoomNotFoundException;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.domain.error.UserNotInRoomException;
import com.megamainmeeting.domain.open.*;
import com.megamainmeeting.spring.base.BaseResponse;
import com.megamainmeeting.spring.base.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserOpeningController {

    @Autowired
    UserOpeningCheck userOpeningCheck;
    @Autowired
    UserOpensRepository userOpensRepository;

    @PostMapping("user/open")
    public BaseResponse<?> userOpen(@RequestAttribute("UserId") long userId,
                                    @RequestBody UserOpens userOpens) throws RoomNotFoundException, UserNotInRoomException, UserNotFoundException {
        userOpens.setUserId(userId);
        userOpeningCheck.addUserOpen(userOpens);
        return SuccessResponse.getSimpleSuccessResponse();
    }

    @PostMapping("user/open/available")
    public BaseResponse<UserOpensSet> getOpensAvailable(@RequestAttribute("UserId") long userId,
                                                        @RequestBody long roomId) throws RoomNotFoundException, UserNotInRoomException {
        Room room = userOpensRepository.getRoom(roomId);
        User user = room.getUser(userId);
        UserOpensSet userOpensSet = UserOpensSet.getInstanceAvailable(user, roomId);
        return SuccessResponse.getSuccessInstance(userOpensSet);
    }

    @PostMapping("room/blocking")
    public BaseResponse<RoomBlockingStatus> getRoomBlockingStatus(@RequestAttribute("UserId") long userId,
                                                                  @RequestBody long roomId) throws RoomNotFoundException, UserNotInRoomException {
        Room room = userOpensRepository.getRoom(roomId);
        room.checkIsUserInRoom(userId);
        RoomBlockingStatus roomBlockingStatus = RoomBlockingStatus.getInstance(room);
        return SuccessResponse.getSuccessInstance(roomBlockingStatus);
    }
}
