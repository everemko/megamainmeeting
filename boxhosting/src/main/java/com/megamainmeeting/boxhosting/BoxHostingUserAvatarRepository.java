package com.megamainmeeting.boxhosting;

import com.megamainmeeting.domain.useravatar.UserProfileAvatarRepository;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class BoxHostingUserAvatarRepository implements UserProfileAvatarRepository {

    private static final String TAG = BoxHostingUserAvatarRepository.class.getSimpleName();
    private static final String USER_AVATAR_INFO_NOT_FOUND = "Image avatar info not found";

    @Inject
    Logger logger;
    @Inject
    BoxFileHosting boxFileHosting;
    @Inject
    UserAvatarInfoRepository imageInfoRepository;

    @Override
    public String getDownloadLink(long userId) {
        try {
            UserAvatarInfo userAvatarInfo = imageInfoRepository.getImageInfoByUserId(userId);
            return boxFileHosting.getDownloadLink(userAvatarInfo.getUrl());
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public String save(long userId, ByteArrayInputStream stream) throws IOException {
        String url = boxFileHosting.saveImage(stream);
        NewUserAvatarInfo newUserAvatarInfo = new NewUserAvatarInfo();
        newUserAvatarInfo.setUrl(url);
        newUserAvatarInfo.setUserId(userId);
        imageInfoRepository.saveImageInfo(newUserAvatarInfo);
        return boxFileHosting.getDownloadLink(url);
    }

    @Override
    public void delete(long userId) {
        try {
            UserAvatarInfo userAvatarInfo = imageInfoRepository.getImageInfoByUserId(userId);
            imageInfoRepository.deleteInfoByUserId(userId);
            boxFileHosting.deleteImage(userAvatarInfo.getUrl());
        } catch (FileNotFoundException e) {
            logger.error(MarkerFactory.getMarker(TAG), USER_AVATAR_INFO_NOT_FOUND);
        }
    }

    @Override
    public boolean isExist(long userId) {
        try {
            return imageInfoRepository.getImageInfoByUserId(userId) != null;
        } catch (Exception e) {
            return false;
        }
    }
}
