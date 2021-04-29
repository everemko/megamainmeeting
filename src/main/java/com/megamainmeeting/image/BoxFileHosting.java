package com.megamainmeeting.image;

import com.box.sdk.BoxConfig;
import com.box.sdk.BoxDeveloperEditionAPIConnection;
import com.box.sdk.BoxFile;
import com.box.sdk.BoxFolder;
import com.megamainmeeting.domain.ImageRepository;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class BoxFileHosting implements ImageRepository {

    private BoxDeveloperEditionAPIConnection api;

    public BoxFileHosting() throws IOException {
        Reader reader = new FileReader("config.json");
        BoxConfig config = BoxConfig.readFrom(reader);
        api = BoxDeveloperEditionAPIConnection.getAppEnterpriseConnection(config);
//        api.authenticate();
    }

    @Override
    public String saveAvatar(InputStream stream) throws FileNotFoundException, IOException{
        BoxFolder rootFolder = BoxFolder.getRootFolder(api);
        BoxFile.Info newFileInfo = rootFolder.uploadFile(stream, String.valueOf(System.nanoTime()));
        stream.close();
        return null;
    }
}
