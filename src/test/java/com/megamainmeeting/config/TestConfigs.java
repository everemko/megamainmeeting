package com.megamainmeeting.config;

import com.megamainmeeting.db.OpenRequestRepositoryJpa;
import com.megamainmeeting.db.RoomRepositoryJpa;
import com.megamainmeeting.db.UserOpenUpRepositoryJpa;
import com.megamainmeeting.db.UserRepositoryJpa;
import com.megamainmeeting.domain.RoomRepository;
import com.megamainmeeting.domain.open.UserOpensRepository;
import com.megamainmeeting.utils.TestValues;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfigs {

    @Bean
    TestValues provideTestValues(UserRepositoryJpa userRepositoryJpa,
                                 RoomRepository roomRepository,
                                 RoomRepositoryJpa roomRepositoryJpa,
                                 UserOpensRepository userOpensRepository,
                                 UserOpenUpRepositoryJpa userOpenUpRepositoryJpa,
                                 OpenRequestRepositoryJpa openRequestRepositoryJpa){
        return new TestValues(userRepositoryJpa,
                roomRepository,
                roomRepositoryJpa,
                userOpensRepository,
                userOpenUpRepositoryJpa,
                openRequestRepositoryJpa);
    }
}
