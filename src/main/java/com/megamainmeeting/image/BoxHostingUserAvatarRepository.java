package com.megamainmeeting.image;

import com.megamainmeeting.db.BoxHostingUserAvatarRepositoryJpa;
import com.megamainmeeting.db.dto.BoxHostingUserAvatarDb;
import com.megamainmeeting.domain.error.ErrorMessages;
import com.megamainmeeting.spring.controller.user.UserProfileAvatarRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

@Component
public class BoxHostingUserAvatarRepository implements UserProfileAvatarRepository {

    @Autowired
    BoxFileHosting boxFileHosting;
    @Autowired
    BoxHostingUserAvatarRepositoryJpa boxHostingUserAvatarRepositoryJpa;

    @Override
    public String getDownloadLink(long userId) {
        BoxHostingUserAvatarDb avatarDb = boxHostingUserAvatarRepositoryJpa.findByUserId(userId);
        if (avatarDb == null) return "";
        return boxFileHosting.getDownloadLink(avatarDb.getImageUrl());
    }

    @Override
    public String save(long userId, ByteArrayInputStream stream) throws IOException {
        String url = boxFileHosting.saveImage(stream);
        BoxHostingUserAvatarDb avatarDb = new BoxHostingUserAvatarDb();
        avatarDb.setImageUrl(url);
        avatarDb.setUserId(userId);
        boxHostingUserAvatarRepositoryJpa.save(avatarDb);
        return boxFileHosting.getDownloadLink(url);
    }

    @Override
    public void delete(long userId) {
        BoxHostingUserAvatarDb avatarDb = boxHostingUserAvatarRepositoryJpa.findByUserId(userId);
        if (avatarDb == null) return;
        boxHostingUserAvatarRepositoryJpa.delete(avatarDb);
    }

    @Override
    public boolean isExist(long userId) {
        return boxHostingUserAvatarRepositoryJpa.findByUserId(userId) != null;
    }
}
