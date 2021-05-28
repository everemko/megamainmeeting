package com.megamainmeeting.image;

import com.megamainmeeting.db.BoxHostingImageRepositoryJpa;
import com.megamainmeeting.db.dto.BoxHostingImageDb;
import com.megamainmeeting.domain.ImageRepository;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Component
public class BoxImageRepository implements ImageRepository {
    @Autowired
    BoxFileHosting boxFileHosting;
    @Autowired
    BoxHostingImageRepositoryJpa boxHostingImageRepositoryJpa;

    @Override
    public long saveImage(InputStream stream) throws FileNotFoundException, IOException {
        String url = boxFileHosting.saveImage(stream);
        BoxHostingImageDb boxHostingImageDb = new BoxHostingImageDb();
        boxHostingImageDb.setImageUrl(url);
        boxHostingImageDb = boxHostingImageRepositoryJpa.save(boxHostingImageDb);
        return boxHostingImageDb.getId();
    }

    @Override
    public void deleteImage(long id) {
        BoxHostingImageDb boxHostingImageDb = boxHostingImageRepositoryJpa.findById(id).orElse(null);
        if(boxHostingImageDb == null) return;
        boxFileHosting.deleteImage(boxHostingImageDb.getImageUrl());
        boxHostingImageRepositoryJpa.delete(boxHostingImageDb);
    }

    @Override
    public String getDownloadLink(long id) {
        BoxHostingImageDb boxHostingImageDb = boxHostingImageRepositoryJpa.findById(id).orElse(null);
        if(boxHostingImageDb == null) return null;
        return boxFileHosting.getDownloadLink(boxHostingImageDb.getImageUrl());
    }

    @Override
    public long saveImage(byte[] image) throws FileNotFoundException, IOException {
        return saveImage(new ByteArrayInputStream(image));
    }
}
