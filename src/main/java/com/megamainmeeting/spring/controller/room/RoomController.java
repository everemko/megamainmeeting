package com.megamainmeeting.spring.controller.room;

import com.megamainmeeting.domain.block.RoomBlockReason;
import com.megamainmeeting.domain.error.SessionNotFoundException;
import com.megamainmeeting.interactor.RoomInteractor;
import com.megamainmeeting.dto.RoomResponse;
import com.megamainmeeting.spring.base.BaseResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@RestController
@AllArgsConstructor
public class RoomController {

    private final RoomInteractor roomInteractor;

    @GetMapping("/chat/rooms")
    public BaseResponse<List<RoomResponse>> getRooms(@RequestAttribute("UserId") long userId) throws SessionNotFoundException {
        List<RoomResponse> rooms = roomInteractor.getRooms(userId);
        return BaseResponse.getSuccessInstance(rooms);
    }

    @PostMapping("chat/room/remove/{id}")
    public BaseResponse<Void> remove(@RequestAttribute("UserId") long userId,
                                     @PathVariable long id) throws Exception {
        roomInteractor.removeRoom(userId, id);
        return BaseResponse.getSuccessInstance(null);
    }

    @GetMapping("chat/room/block")
    public BaseResponse<RoomBlockReason[]> getBlocking(){
        return BaseResponse.getSuccessInstance(RoomBlockReason.values());
    }

    @PostMapping("chat/room/block")
    public BaseResponse<Void> block(@RequestAttribute("UserId") long userId,
                                    @RequestBody RoomBlock roomBlock
    ) throws Exception{
        roomBlock.setUserId(userId);
        roomInteractor.blockRoom(roomBlock);
        return BaseResponse.getSuccessInstance(null);
    }
}
