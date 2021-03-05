package com.megamainmeeting.spring.controller.room;

import com.megamainmeeting.domain.error.SessionNotFoundException;
import com.megamainmeeting.domain.interactor.RoomInteractor;
import com.megamainmeeting.entity.chat.Room;
import com.megamainmeeting.spring.base.BaseResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@Component
@AllArgsConstructor
public class RoomController {

    private final RoomInteractor roomInteractor;

    @GetMapping("/chat/rooms")
    BaseResponse<List<Room>> getRooms(@RequestHeader String token) throws SessionNotFoundException {
        List<Room> rooms = roomInteractor.getRooms(token);
        return BaseResponse.getSuccessInstance(rooms);
    }
}
