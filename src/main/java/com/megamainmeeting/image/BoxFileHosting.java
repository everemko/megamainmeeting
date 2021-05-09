package com.megamainmeeting.image;

import com.box.sdk.*;
import com.megamainmeeting.domain.ImageRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class BoxFileHosting implements ImageRepository {

    @Autowired
    private Logger logger;

    private BoxDeveloperEditionAPIConnection api;

    public BoxFileHosting() throws IOException {
        Reader reader = new InputStreamReader(this.getClass().getResourceAsStream("/813834594_n2fnopuu_config.json"));
        BoxConfig config = BoxConfig.readFrom(reader);
        api = BoxDeveloperEditionAPIConnection.getAppEnterpriseConnection(config);
    }

    @Override
    public String saveImage(InputStream stream) throws IOException {
        try (stream) {
            BoxFolder rootFolder = BoxFolder.getRootFolder(api);
            BoxFile.Info newFileInfo = rootFolder.uploadFile(stream, generateAvatarName());
            stream.close();
            BoxFile file = new BoxFile(api, newFileInfo.getID());
            BoxSharedLink sharedLink = getSharedLink(file);
            newFileInfo.setSharedLink(sharedLink);
            return sharedLink.getURL();
        }
    }

    @Override
    public String saveImage(byte[] image) throws FileNotFoundException, IOException {
        return saveImage(new ByteArrayInputStream(image));
    }

    @Override
    public void deleteImage(String url) {
        try {
            if(url == null) return;
            BoxItem.Info itemInfo = BoxItem.getSharedItem(api, url);
            BoxFile file = new BoxFile(api, itemInfo.getID());
            file.delete();
        } catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    @Override
    public String getDownloadLink(String url) {
        try {
            if(url == null) return null;
            BoxItem.Info itemInfo = BoxItem.getSharedItem(api, url);
            return itemInfo.getSharedLink().getDownloadURL();
        } catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }
    }

    private BoxSharedLink getSharedLink(BoxFile file) {
        BoxSharedLink.Permissions permissions = new BoxSharedLink.Permissions();
        permissions.setCanDownload(true);
        permissions.setCanPreview(true);
        return file.createSharedLink(BoxSharedLink.Access.DEFAULT, null, permissions);
    }

    private String generateAvatarName() {
        return System.nanoTime() + ".jpg";
    }
}
