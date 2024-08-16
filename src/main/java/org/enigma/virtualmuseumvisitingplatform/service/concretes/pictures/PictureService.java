package org.enigma.virtualmuseumvisitingplatform.service.concretes.pictures;

import lombok.RequiredArgsConstructor;
import org.enigma.virtualmuseumvisitingplatform.core.utilities.pictures.BaseReadAndWritePicture;
import org.enigma.virtualmuseumvisitingplatform.service.abstracts.pictures.IPictureService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PictureService implements IPictureService {

    private final BaseReadAndWritePicture readAndWritePicture;

    @Value("${picture.path}")
    private String picturePath;

    @Override
    public byte[] getPicture(String fileName) {
        return readAndWritePicture.readPicture(picturePath + fileName);
    }
}
