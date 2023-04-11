package com.megamainmeeting.boxhosting;

import com.megamainmeeting.boxhosting.exception.ImageInfoNotFound;

import java.io.FileNotFoundException;

public interface ImageInfoRepository {

    public ImageInfo saveImageInfo(NewImageInfo imageInfo);

    public ImageInfo getImageInfo(long id) throws ImageInfoNotFound;

    void deleteInfo(long id);
}
