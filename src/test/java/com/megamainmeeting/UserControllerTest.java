package com.megamainmeeting;

import com.megamainmeeting.domain.ImageRepository;
import com.megamainmeeting.utils.TestValues;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest extends BaseTest{

    @Autowired
    TestValues testValues;
    @Autowired
    ImageRepository imageRepository;

    @Test
    public void test() throws FileNotFoundException, IOException {
        FileInputStream file = new FileInputStream(this.getClass().getResource("/testImage.png").getFile());
        long imageId = imageRepository.saveImage(file);
        Assert.assertNotEquals(0, imageId);
    }
}
