package com.megamainmeeting.spring.controller.user;

import com.megamainmeeting.database.UserRepositoryJpa;
import com.megamainmeeting.database.dto.UserDb;
import com.megamainmeeting.database.dto.UserProfileDb;
import com.megamainmeeting.domain.error.AvatarSizeException;
import com.megamainmeeting.domain.error.BadDataException;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.domain.useravatar.UpdateAvatarInteractor;
import com.megamainmeeting.domain.useravatar.UserProfileAvatarRepository;
import com.megamainmeeting.entity.user.Gender;
import com.megamainmeeting.entity.user.UserProfile;
import com.megamainmeeting.spring.base.BaseResponse;
import com.megamainmeeting.spring.base.SuccessResponse;
import com.megamainmeeting.spring.controller.Endpoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Component
@RestController
@RequestMapping(path = Endpoints.BASE_API)
public class UserController {

    @Autowired
    UserRepositoryJpa userRepositoryJpa;
    @Autowired
    UpdateAvatarInteractor updateAvatarInteractor;
    @Autowired
    UserProfileAvatarRepository userProfileAvatarRepository;

    @PostMapping("user/profile/update")
    BaseResponse<?> update(
            @RequestHeader("UserId") long userId,
            @RequestBody UserProfile userProfile) throws Exception {
        userProfile.setUserId(userId);
        Optional<UserDb> optional = userRepositoryJpa.findById(userProfile.getUserId());
        if (optional.isEmpty()) throw new UserNotFoundException();
        UserDb user = optional.get();
        UserProfileDb userProfileDb = user.getUserProfile();
        if (userProfileDb == null) {
            userProfileDb = new UserProfileDb();
            userProfileDb.setUser(user);
        }
        userProfileDb.setCity(userProfile.getCity());
        userProfileDb.setCountry(userProfile.getCountry());
        userProfileDb.setAboutMyself(userProfile.getAboutMyself());
        userProfileDb.setFirstDateIdeal(userProfile.getFirstDateIdeal());
        userProfileDb.setHeight(userProfile.getHeight());
        userProfileDb.setProfession(userProfile.getProfession());
        userProfileDb.setWeight(userProfile.getWeight());
        user.setUserProfile(userProfileDb);
        userRepositoryJpa.save(user);
        return SuccessResponse.getSuccessInstance(null);
    }

    @GetMapping("user/profile")
    BaseResponse<UserProfile> getUserProfile(@RequestHeader("UserId") long userId) throws Exception {
        Optional<UserDb> optional = userRepositoryJpa.findById(userId);
        if (optional.isEmpty()) throw new UserNotFoundException();
        UserDb user = optional.get();
        UserProfileDb userProfileDb = user.getUserProfile();
        UserProfile userProfile = new UserProfile();
        userProfile.setUserId(userId);
        if (userProfileDb == null) return SuccessResponse.getSuccessInstance(userProfile);
        userProfile.setCity(userProfileDb.getCity());
        userProfile.setCountry(userProfileDb.getCountry());
        userProfile.setAboutMyself(userProfileDb.getAboutMyself());
        userProfile.setFirstDateIdeal(userProfileDb.getFirstDateIdeal());
        userProfile.setHeight(userProfileDb.getHeight());
        userProfile.setProfession(userProfileDb.getProfession());
        userProfile.setWeight(userProfileDb.getWeight());
        userProfile.setAvatar(userProfileAvatarRepository.getDownloadLink(userId));
        return SuccessResponse.getSuccessInstance(userProfile);
    }

    @PostMapping("user/profile/avatar")
    public BaseResponse<String> postUserAvatar(
            @RequestHeader("UserId") long userId,
            @RequestPart("avatar") MultipartFile file
    ) throws UserNotFoundException, IOException, AvatarSizeException {
        String url = updateAvatarInteractor.updatePhoto(userId, file.getBytes());
        return SuccessResponse.getSuccessInstance(url);
    }

    @DeleteMapping("user/profile/avatar")
    public BaseResponse<Object> deleteUserAvatar(
            @RequestHeader("UserId") long userId
    ) throws UserNotFoundException {
        updateAvatarInteractor.deletePhoto(userId);
        return SuccessResponse.getSimpleSuccessResponse();
    }

    @PostMapping("user/gender/match")
    public BaseResponse<Void> postMatchGender(
            @RequestHeader("UserId") long userid,
            @RequestBody Gender gender) throws UserNotFoundException, BadDataException {
        if (gender == null) throw new BadDataException();
        UserDb userDb = userRepositoryJpa.findById(userid).orElseThrow(UserNotFoundException::new);
        userDb.setGenderMatch(gender);
        userRepositoryJpa.save(userDb);
        return SuccessResponse.getSuccessInstance(null);
    }


}
