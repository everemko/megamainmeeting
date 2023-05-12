package com.megamainmeeting.database.onevalue;

import com.megamainmeeting.database.AppConfig;
import com.megamainmeeting.database.Application;
import com.megamainmeeting.database.repository.OpensBeforeUserBlockedRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ContextConfiguration(classes = AppConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class OpensBeforeUserBlockedRepositoryImplTest {

    public static final long TEST_VALUE = 1234;

    @Autowired
    OpensBeforeUserBlockedRepository opensBeforeUserBlockedRepository;

    @Test
    public void testPositive(){
        opensBeforeUserBlockedRepository.setValue(TEST_VALUE);
        long value = opensBeforeUserBlockedRepository.getValue();
        Assert.assertEquals(TEST_VALUE, value);
    }
}
