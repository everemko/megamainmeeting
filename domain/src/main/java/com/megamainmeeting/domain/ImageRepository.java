package com.megamainmeeting.domain;

import java.io.*;
import java.util.stream.Stream;

public interface ImageRepository {

    //return imageId
    public long saveImage(InputStream stream) throws FileNotFoundException, IOException;

    public void deleteImage(long id);

    public String getDownloadLink(long id) throws FileNotFoundException;

    //return imageId
    public long saveImage(byte[] image) throws FileNotFoundException, IOException;
}
