package com.megamainmeeting.spring.controller.admin;

import com.megamainmeeting.database.repository.OpensBeforeUserBlockedRepository;
import com.megamainmeeting.spring.base.BaseResponse;
import com.megamainmeeting.spring.base.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestAdminController {

    @Autowired
    OpensBeforeUserBlockedRepository opensBeforeUserBlockedRepository;

    @PutMapping("admin/opensbeforeuserblocked")
    public BaseResponse setOpensBeforeUserBlocked(@RequestParam("value") long value) {
        opensBeforeUserBlockedRepository.setValue(value);
        return SuccessResponse.getSimpleSuccessResponse();
    }
}
