package com.megamainmeeting.database.repository;

import com.megamainmeeting.boxhosting.ImageInfo;
import com.megamainmeeting.boxhosting.ImageInfoRepository;
import com.megamainmeeting.boxhosting.NewImageInfo;
import com.megamainmeeting.boxhosting.exception.ImageInfoNotFound;
import com.megamainmeeting.database.BoxHostingImageRepositoryJpa;
import com.megamainmeeting.database.dto.BoxHostingImageDb;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class ImageInfoRepositoryImpl implements ImageInfoRepository {

    @Inject
    private BoxHostingImageRepositoryJpa boxHostingImageRepositoryJpa;

    @Override
    public ImageInfo saveImageInfo(NewImageInfo newImageInfo) {
        BoxHostingImageDb boxHostingImageDb = new BoxHostingImageDb();
        boxHostingImageDb.setImageUrl(newImageInfo.getUrl());
        boxHostingImageDb = boxHostingImageRepositoryJpa.save(boxHostingImageDb);
        ImageInfo imageInfo = new ImageInfo();
        imageInfo.setId(boxHostingImageDb.getId());
        imageInfo.setUrl(boxHostingImageDb.getImageUrl());
        return imageInfo;
    }

    @Override
    public ImageInfo getImageInfo(long id) throws ImageInfoNotFound {
        BoxHostingImageDb imageDb = boxHostingImageRepositoryJpa.findById(id).orElseThrow(ImageInfoNotFound::new);
        ImageInfo imageInfo = new ImageInfo();
        imageInfo.setUrl(imageDb.getImageUrl());
        imageInfo.setId(imageDb.getId());
        return imageInfo;
    }

    @Override
    public void deleteInfo(long id) {
        boxHostingImageRepositoryJpa.deleteById(id);
    }
}
