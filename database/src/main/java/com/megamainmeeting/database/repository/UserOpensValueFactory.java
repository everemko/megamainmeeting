package com.megamainmeeting.database.repository;

import com.megamainmeeting.domain.open.UserOpenType;
import com.megamainmeeting.domain.useravatar.UserProfileAvatarRepository;
import com.megamainmeeting.database.dto.UserProfileDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserOpensValueFactory {

    @Autowired
    UserProfileAvatarRepository userProfileAvatarRepository;

    public String getByType(UserProfileDb userProfileDb, UserOpenType type){
        switch (type){
            case City: return userProfileDb.getCity();
            case Weight: return String.valueOf(userProfileDb.getWeight());
            case Height: return String.valueOf(userProfileDb.getHeight());
            case Country: return userProfileDb.getCountry();
            case Profession: return userProfileDb.getProfession();
            case AboutMyself: return userProfileDb.getAboutMyself();
            case FirstDateIdeal: return userProfileDb.getFirstDateIdeal();
            case Photo: return userProfileAvatarRepository.getDownloadLink(userProfileDb.getUser().getId());
        }
        return null;
    }
}
