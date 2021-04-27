package com.megamainmeeting.spring.controller.opening;

import com.megamainmeeting.domain.error.OpenRequestNotFoundException;
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
                                                             @RequestBody long openRequestId) throws RoomNotFoundException,
            UserNotInRoomException, OpenRequestNotFoundException  {
        Room room = userOpensRepository.getRoomByOpenRequestId(openRequestId);
        User user = room.getUser(userId);
        return SuccessResponse.getSuccessInstance(user.getAvailable());
    }

    @PostMapping("room/blocking")
    public BaseResponse<RoomBlockingStatus> getRoomBlockingStatus(@RequestAttribute("UserId") long userId,
                                                                  @RequestBody long roomId) throws RoomNotFoundException,
            UserNotInRoomException, OpenRequestNotFoundException {
        Room room = userOpensRepository.getRoom(roomId);
        room.checkIsUserInRoom(userId);
        RoomBlockingStatus roomBlockingStatus = RoomBlockingStatus.getInstance(room);
        return SuccessResponse.getSuccessInstance(roomBlockingStatus);
    }
}
