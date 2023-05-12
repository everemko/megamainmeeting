package com.megamainmeeting.database;

import com.megamainmeeting.boxhosting.BoxImageRepository;
import com.megamainmeeting.database.repository.ImageInfoRepositoryImpl;
import com.megamainmeeting.domain.ImageRepository;
import com.megamainmeeting.domain.useravatar.UserProfileAvatarRepository;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mockito.Mockito;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
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

@TestConfiguration
public class AppConfig {

    ImageRepository imageRepository = Mockito.mock(ImageRepository.class);
    UserProfileAvatarRepository userProfileAvatarRepository = Mockito.mock(UserProfileAvatarRepository.class);

    @Bean
    Logger provideLogger(){
        return LogManager.getLogger();
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
