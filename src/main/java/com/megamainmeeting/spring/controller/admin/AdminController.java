package com.megamainmeeting.spring.controller.admin;

import com.megamainmeeting.database.OneValueDbRepositoryJpa;
import com.megamainmeeting.database.repository.OpensBeforeUserBlockedRepository;
import com.megamainmeeting.spring.base.BaseResponse;
import com.megamainmeeting.spring.base.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.Externalizable;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Controller
public class AdminController {

    @Autowired
    OpensBeforeUserBlockedRepository opensBeforeUserBlockedRepository;
    @Value("${server.port}")
    int aPort;

    @GetMapping("admin")
    public String getAdminPanel(Model model) {
        String localHostName = "";
        try {
            localHostName = InetAddress.getLocalHost().getHostAddress() + ":" + aPort;
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        long opensBeforeBlocked = 0;
        try {
            opensBeforeBlocked = opensBeforeUserBlockedRepository.getValue();
        } catch (Exception ignored) {

        }
        model.addAttribute("opensbeforeuserblocked", opensBeforeBlocked);
        model.addAttribute("currentServerIp", localHostName);
        return "admin";
    }
}
