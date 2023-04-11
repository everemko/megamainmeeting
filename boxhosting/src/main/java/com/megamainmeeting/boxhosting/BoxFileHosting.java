package com.megamainmeeting.boxhosting;

import com.box.sdk.*;
import com.megamainmeeting.domain.ImageRepository;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.io.*;

public class BoxFileHosting {

    @Inject
    private Logger logger;

    private final BoxDeveloperEditionAPIConnection api;

    public BoxFileHosting() {
        BoxDeveloperEditionAPIConnection api1;
        try {
            Reader reader = new InputStreamReader(this.getClass().getResourceAsStream("/813834594_n2fnopuu_config.json"));
            BoxConfig config = BoxConfig.readFrom(reader);
            api1 = BoxDeveloperEditionAPIConnection.getAppEnterpriseConnection(config);
        } catch (IOException ioException){
            logger.error("Can not read box hosting config");
            api1 = null;
        }
        api = api1;
    }

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

    public String saveImage(byte[] image) throws FileNotFoundException, IOException {
        return saveImage(new ByteArrayInputStream(image));
    }

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
