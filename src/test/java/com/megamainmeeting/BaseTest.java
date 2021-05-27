package com.megamainmeeting;

import com.megamainmeeting.config.AppConfigTest;
import com.megamainmeeting.config.ServiceConfig;
import com.megamainmeeting.config.TestConfigs;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(classes = Application.class)

@ContextConfiguration(classes = {AppConfigTest.class, TestConfigs.class, ServiceConfig.class})
abstract class BaseTest {

}
