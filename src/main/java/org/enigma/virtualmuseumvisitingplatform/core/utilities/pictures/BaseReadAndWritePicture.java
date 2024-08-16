package org.enigma.virtualmuseumvisitingplatform.core.utilities.pictures;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

public  abstract class BaseReadAndWritePicture {

    @Value("${picture.path}")
    protected String picturePath;

    public abstract byte[] readPicture(String path);
    public abstract void writePicture(MultipartFile file, String path);
}
