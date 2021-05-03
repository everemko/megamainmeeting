package com.megamainmeeting.image;

import com.box.sdk.*;
import com.megamainmeeting.domain.ImageRepository;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class BoxFileHosting implements ImageRepository {

    private BoxDeveloperEditionAPIConnection api;

    public BoxFileHosting() throws IOException {
        Reader reader = new InputStreamReader(this.getClass().getResourceAsStream("/813834594_n2fnopuu_config.json"));
        BoxConfig config = BoxConfig.readFrom(reader);
        api = BoxDeveloperEditionAPIConnection.getAppEnterpriseConnection(config);
    }

    @Override
    public String saveAvatar(InputStream stream) throws IOException{
        try (stream) {
            BoxFolder rootFolder = BoxFolder.getRootFolder(api);
            BoxFile.Info newFileInfo = rootFolder.uploadFile(stream, generateAvatarName());
            stream.close();
            BoxFile file = new BoxFile(api, newFileInfo.getID());
            BoxSharedLink sharedLink = getSharedLink(file);
            newFileInfo.setSharedLink(sharedLink);
            return sharedLink.getDownloadURL();
        }
    }

    private BoxSharedLink getSharedLink(BoxFile file){
        BoxSharedLink.Permissions permissions = new BoxSharedLink.Permissions();
        permissions.setCanDownload(true);
        permissions.setCanPreview(true);
        return file.createSharedLink(BoxSharedLink.Access.OPEN, null, permissions);
    }

    private String generateAvatarName(){
        return System.nanoTime() + ".jpg";
    }
}
