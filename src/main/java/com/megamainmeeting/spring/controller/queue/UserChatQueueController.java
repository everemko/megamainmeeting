package com.megamainmeeting.spring.controller.queue;

import com.megamainmeeting.domain.UserChatCandidateQueue;
import com.megamainmeeting.spring.controller.Endpoints;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = Endpoints.BASE_API)
@AllArgsConstructor
public class UserChatQueueController {

    public static final String QUEUE = "/queue";

    @Autowired
    UserChatCandidateQueue userChatCandidateQueue;

    @GetMapping(QUEUE)
    public List<Long> getQueue(){
        return userChatCandidateQueue.getQueue();
    }
}
