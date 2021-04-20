package com.megamainmeeting.spring.controller.user;

import com.megamainmeeting.db.UserRepositoryJpa;
import com.megamainmeeting.db.dto.UserDb;
import com.megamainmeeting.db.dto.UserProfileDb;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.entity.user.User;
import com.megamainmeeting.spring.base.BaseResponse;
import com.megamainmeeting.spring.base.SuccessResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Component
@RestController
@AllArgsConstructor
public class UserController {

    @Autowired
    UserRepositoryJpa userRepositoryJpa;

    @PostMapping("user/profile/update")
    BaseResponse<?> update(
            @RequestAttribute("UserId") long userId,
            @RequestBody UserProfile userProfile) throws Exception{
        userProfile.setUserId(userId);
        Optional<UserDb> optional = userRepositoryJpa.findById(userProfile.getUserId());
        if(optional.isEmpty()) throw new UserNotFoundException();
        UserDb user = optional.get();
        UserProfileDb userProfileDb = user.getUserProfile();
        if(userProfileDb == null) {
            userProfileDb = new UserProfileDb();
            userProfileDb.setUser(user);
        }
        userProfileDb.setCity(userProfile.getCity());
        userProfileDb.setCountry(userProfile.getCountry());
        userProfileDb.setAboutMyself(userProfile.getAboutMyself());
        userProfileDb.setFirstDateIdeal(userProfile.getFirstDateIdeal());
        userProfileDb.setHeight(userProfile.getHeight());
        userProfileDb.setProfession(userProfile.getProfession());
        userProfileDb.setWight(userProfile.getWight());
        user.setUserProfile(userProfileDb);
        userRepositoryJpa.save(user);
        return SuccessResponse.getSuccessInstance(null);
    }

    @GetMapping("user/profile")
    BaseResponse<UserProfile> getUserProfile(@RequestAttribute("UserId") long userId) throws Exception{
        Optional<UserDb> optional = userRepositoryJpa.findById(userId);
        if(optional.isEmpty()) throw new UserNotFoundException();
        UserDb user = optional.get();
        UserProfileDb userProfileDb = user.getUserProfile();
        UserProfile userProfile = new UserProfile();
        userProfile.setUserId(userId);
        if(userProfileDb == null) return SuccessResponse.getSuccessInstance(userProfile);
        userProfile.setCity(userProfileDb.getCity());
        userProfile.setCountry(userProfileDb.getCountry());
        userProfile.setAboutMyself(userProfileDb.getAboutMyself());
        userProfile.setFirstDateIdeal(userProfileDb.getFirstDateIdeal());
        userProfile.setHeight(userProfileDb.getHeight());
        userProfile.setProfession(userProfileDb.getProfession());
        userProfile.setWight(userProfileDb.getWight());
        return SuccessResponse.getSuccessInstance(userProfile);
    }
}
