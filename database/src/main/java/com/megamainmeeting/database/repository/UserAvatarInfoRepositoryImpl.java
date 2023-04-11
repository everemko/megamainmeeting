package com.megamainmeeting.database.repository;

import com.megamainmeeting.boxhosting.NewUserAvatarInfo;
import com.megamainmeeting.boxhosting.UserAvatarInfo;
import com.megamainmeeting.boxhosting.UserAvatarInfoRepository;
import com.megamainmeeting.database.BoxHostingImageRepositoryJpa;
import com.megamainmeeting.database.BoxHostingUserAvatarRepositoryJpa;
import com.megamainmeeting.database.dto.BoxHostingUserAvatarDb;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.FileNotFoundException;

@Component
public class UserAvatarInfoRepositoryImpl implements UserAvatarInfoRepository {

    @Inject
    private BoxHostingUserAvatarRepositoryJpa boxHostingImageRepositoryJpa;

    @Override
    public UserAvatarInfo saveImageInfo(NewUserAvatarInfo imageInfo) {
        BoxHostingUserAvatarDb avatarInfoDb = new BoxHostingUserAvatarDb();
        avatarInfoDb.setImageUrl(imageInfo.getUrl());
        avatarInfoDb.setUserId(imageInfo.getUserId());
        boxHostingImageRepositoryJpa.save(avatarInfoDb);
        return map(avatarInfoDb);
    }

    @Override
    public UserAvatarInfo getImageInfoByUserId(long id) throws FileNotFoundException {
        BoxHostingUserAvatarDb avatar = boxHostingImageRepositoryJpa.findByUserId(id)
                .orElseThrow(FileNotFoundException::new);
        return map(avatar);
    }

    @Override
    public void deleteInfoByUserId(long id) {
        boxHostingImageRepositoryJpa.deleteById(id);
    }

    private UserAvatarInfo map(BoxHostingUserAvatarDb avatar){
        UserAvatarInfo userAvatarInfo = new UserAvatarInfo();
        userAvatarInfo.setId(avatar.getId());
        userAvatarInfo.setUrl(avatar.getImageUrl());
        userAvatarInfo.setUserId(avatar.getUserId());
        return userAvatarInfo;
    }
}
