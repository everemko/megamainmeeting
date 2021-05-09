package com.megamainmeeting.domain;

import java.io.*;
import java.util.stream.Stream;

public interface ImageRepository {

    public String saveImage(InputStream stream) throws FileNotFoundException, IOException;

    public void deleteImage(String url);

    public String getDownloadLink(String url);

    public String saveImage(byte[] image) throws FileNotFoundException, IOException;
}
