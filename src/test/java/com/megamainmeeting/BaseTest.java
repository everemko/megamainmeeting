package com.megamainmeeting;

import com.megamainmeeting.config.AppConfigTest;
import com.megamainmeeting.config.RepositoryConfigTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(classes = Application.class)
@ContextConfiguration(classes = {AppConfigTest.class, RepositoryConfigTest.class})
abstract class BaseTest {

}
