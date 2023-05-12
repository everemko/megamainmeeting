package com.megamainmeeting.spring.controller.room;

import com.megamainmeeting.database.RoomRepositoryJpa;
import com.megamainmeeting.database.dto.RoomDb;
import com.megamainmeeting.spring.controller.Endpoints;
import com.megamainmeeting.spring.dto.RoomResponse;
import com.megamainmeeting.domain.block.entity.NewRoomBlock;
import com.megamainmeeting.domain.block.entity.RoomBlockReason;
import com.megamainmeeting.domain.block.RoomInteractor;
import com.megamainmeeting.domain.error.SessionNotFoundException;
import com.megamainmeeting.spring.base.BaseResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RestController
@RequestMapping(path = Endpoints.BASE_API)
@AllArgsConstructor
public class RoomController {

    private final RoomInteractor roomInteractor;
    private final RoomRepositoryJpa roomRepositoryJpa;

    @GetMapping("/chat/rooms")
    public BaseResponse<List<RoomResponse>> getRooms(@RequestHeader("UserId") long userId) throws SessionNotFoundException {
        List<RoomDb> rooms = roomRepositoryJpa.findAllByUserId(userId);
        List<RoomResponse> roomResponses;
        if (rooms == null) roomResponses = Collections.emptyList();
        else roomResponses = rooms.stream()
                .map(RoomResponse::new)
                .collect(Collectors.toList());
        return BaseResponse.getSuccessInstance(roomResponses);
    }

    @PostMapping("chat/room/remove/{id}")
    public BaseResponse<Void> remove(@RequestHeader("UserId") long userId,
                                     @PathVariable long id) throws Exception {
        roomInteractor.removeRoom(userId, id);
        return BaseResponse.getSuccessInstance(null);
    }

    @GetMapping("chat/room/block")
    public BaseResponse<RoomBlockReason[]> getBlocking() {
        return BaseResponse.getSuccessInstance(RoomBlockReason.values());
    }

    @PostMapping("chat/room/block")
    public BaseResponse<Void> block(@RequestHeader("UserId") long userId,
                                    @RequestBody NewRoomBlock roomBlock
    ) throws Exception {
        roomBlock.setUserId(userId);
        roomInteractor.blockRoom(roomBlock);
        return BaseResponse.getSuccessInstance(null);
    }
}
