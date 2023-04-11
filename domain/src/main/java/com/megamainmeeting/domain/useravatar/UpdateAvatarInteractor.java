package com.megamainmeeting.domain.useravatar;

import com.megamainmeeting.domain.UserRepository;
import com.megamainmeeting.domain.error.AvatarSizeException;
import com.megamainmeeting.domain.error.UserNotFoundException;
import lombok.AllArgsConstructor;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class UpdateAvatarInteractor {

    @Inject
    UserRepository userRepositoryJpa;
    @Inject
    UserProfileAvatarRepository userProfileAvatarRepository;


    //Return download link;
    public String updatePhoto(long userId, byte[] avatar) throws UserNotFoundException, IOException, AvatarSizeException {
        if (avatar.length > MAX_IMAGE_SIZE) throw new AvatarSizeException();
        if (!userRepositoryJpa.isExist(userId)) throw new UserNotFoundException();
        userProfileAvatarRepository.delete(userId);
        return userProfileAvatarRepository.save(userId, new ByteArrayInputStream(avatar));
    }

    public void deletePhoto(long userId) throws UserNotFoundException {
        if (!userRepositoryJpa.isExist(userId)) throw new UserNotFoundException();
        userProfileAvatarRepository.delete(userId);
    }

    private static final double MAX_IMAGE_SIZE = 2 * Math.pow(10, 6);
}
