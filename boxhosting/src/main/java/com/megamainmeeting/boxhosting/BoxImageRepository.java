package com.megamainmeeting.boxhosting;

import com.megamainmeeting.boxhosting.exception.ImageInfoNotFound;
import com.megamainmeeting.domain.ImageRepository;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BoxImageRepository implements ImageRepository {

    public static final String TAG = BoxImageRepository.class.getSimpleName();
    @Inject
    Logger logger;
    @Inject
    BoxFileHosting boxFileHosting;
    @Inject
    ImageInfoRepository imageInfoRepository;

    @Override
    public long saveImage(InputStream stream) throws FileNotFoundException, IOException {
        String url = boxFileHosting.saveImage(stream);
        NewImageInfo newImageInfo = new NewImageInfo();
        newImageInfo.setUrl(url);
        ImageInfo imageInfo = imageInfoRepository.saveImageInfo(newImageInfo);
        return imageInfo.getId();
    }

    @Override
    public void deleteImage(long id) {
        try {
            ImageInfo imageInfo = imageInfoRepository.getImageInfo(id);
            boxFileHosting.deleteImage(imageInfo.getUrl());
        } catch (ImageInfoNotFound imageInfoNotFound){
            logger.logp(Level.WARNING, TAG, "getDownloadLink", "Image not found");
        }
        imageInfoRepository.deleteInfo(id);
    }

    @Override
    public String getDownloadLink(long id) {
        try {
            ImageInfo imageInfo = imageInfoRepository.getImageInfo(id);
            return boxFileHosting.getDownloadLink(imageInfo.getUrl());
        } catch (ImageInfoNotFound imageInfoNotFound) {
            logger.logp(Level.WARNING, TAG, "getDownloadLink", "Image not found");
            return "";
        }
    }

    @Override
    public long saveImage(byte[] image) throws FileNotFoundException, IOException {
        return saveImage(new ByteArrayInputStream(image));
    }
}
