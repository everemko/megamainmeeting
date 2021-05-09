package com.megamainmeeting.spring.controller.user;

import com.megamainmeeting.db.UserRepositoryJpa;
import com.megamainmeeting.db.dto.UserDb;
import com.megamainmeeting.db.dto.UserProfileDb;
import com.megamainmeeting.domain.ImageRepository;
import com.megamainmeeting.domain.error.AvatarSizeException;
import com.megamainmeeting.domain.error.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Component
public class UpdateAvatarInteractor {

    @Autowired
    UserRepositoryJpa userRepositoryJpa;
    @Autowired
    ImageRepository imageRepository;


    //Return download link;
    public String updatePhoto(long userId, byte[] avatar) throws UserNotFoundException, IOException, AvatarSizeException {
        if (avatar.length > MAX_IMAGE_SIZE) throw new AvatarSizeException();
        UserDb user = userRepositoryJpa.findById(userId).orElseThrow(UserNotFoundException::new);
        UserProfileDb userProfileDb = user.getUserProfile();
        String oldUrl = userProfileDb.getPhoto();
        String url = imageRepository.saveImage(new ByteArrayInputStream(avatar));
        userProfileDb.setPhoto(url);
        imageRepository.deleteImage(oldUrl);
        userRepositoryJpa.save(user);
        return imageRepository.getDownloadLink(url);
    }

    private static final double MAX_IMAGE_SIZE = 2 * Math.pow(10, 6);
}
