package com.megamainmeeting.database;

import com.megamainmeeting.boxhosting.BoxImageRepository;
import com.megamainmeeting.database.repository.ImageInfoRepositoryImpl;
import com.megamainmeeting.domain.ImageRepository;
import com.megamainmeeting.domain.useravatar.UserProfileAvatarRepository;
import lombok.Getter;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Configuration
@Getter
public class AppConfig {

    ImageRepository imageRepository = Mockito.mock(ImageRepository.class);
    UserProfileAvatarRepository userProfileAvatarRepository = Mockito.mock(UserProfileAvatarRepository.class);

    @Bean
    public Logger logger() {
        return LoggerFactory.getLogger("application");
    }

    @Bean()
    ImageRepository getImageRepository(){
        return imageRepository;
    }

    @Bean
    UserProfileAvatarRepository getUserProfileAvatarRepository(){
        return userProfileAvatarRepository;
    }
}
