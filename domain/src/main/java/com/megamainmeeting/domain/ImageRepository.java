package com.megamainmeeting.domain;

import java.io.*;
import java.util.stream.Stream;

public interface ImageRepository {

    public String saveAvatar(InputStream stream) throws FileNotFoundException, IOException;

    public void deletePhoto(String url);

    public String getDownloadLink(String url);
}
