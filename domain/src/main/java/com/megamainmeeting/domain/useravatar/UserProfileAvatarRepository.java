package com.megamainmeeting.domain.useravatar;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public interface UserProfileAvatarRepository {

    String getDownloadLink(long userId);

    //return downloadLink
    String save(long userId, ByteArrayInputStream stream) throws IOException;

    void delete(long userId);

    boolean isExist(long userId);
}
