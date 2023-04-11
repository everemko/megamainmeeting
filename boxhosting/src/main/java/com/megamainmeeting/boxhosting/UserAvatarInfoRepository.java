package com.megamainmeeting.boxhosting;

import java.io.FileNotFoundException;

public interface UserAvatarInfoRepository {

    public UserAvatarInfo saveImageInfo(NewUserAvatarInfo imageInfo);

    public UserAvatarInfo getImageInfoByUserId(long id) throws FileNotFoundException;

    void deleteInfoByUserId(long userId);
}
