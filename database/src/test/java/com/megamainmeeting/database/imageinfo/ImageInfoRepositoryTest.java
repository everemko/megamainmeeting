package com.megamainmeeting.database.imageinfo;

import com.megamainmeeting.boxhosting.ImageInfo;
import com.megamainmeeting.boxhosting.ImageInfoRepository;
import com.megamainmeeting.boxhosting.NewImageInfo;
import com.megamainmeeting.boxhosting.exception.ImageInfoNotFound;
import com.megamainmeeting.database.Application;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import static com.megamainmeeting.database.imageinfo.ImageInfoConstants.IMAGE_URL_1;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ImageInfoRepositoryTest {

    @Inject
    ImageInfoRepository imageInfoRepository;

    @Test
    public void saveImageInfo() throws ImageInfoNotFound {
        NewImageInfo imageInfo = new NewImageInfo();
        imageInfo.setUrl(IMAGE_URL_1);
        ImageInfo imageInfoSaved = imageInfoRepository.saveImageInfo(imageInfo);
        ImageInfo imageInfoAfterSaving = imageInfoRepository.getImageInfo(imageInfoSaved.getId());
        Assert.assertEquals(imageInfoAfterSaving, imageInfoSaved);
        Assert.assertEquals(imageInfo.getUrl(), imageInfoAfterSaving.getUrl());
    }

    @Test(expected = ImageInfoNotFound.class)
    public void saveImageInfoError() throws ImageInfoNotFound {
        imageInfoRepository.getImageInfo(0);
    }
}
