package org.enigma.virtualmuseumvisitingplatform.core.utilities.pictures;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Component
public class ReadAndWritePicture extends BaseReadAndWritePicture{

    @Override
    public byte[] readPicture(String picturePath) {
        Path path = Paths.get(picturePath);
        byte[] picture = null;
        try {
            picture = Files.readAllBytes(path);
        }
        catch (IOException e){
            e.printStackTrace();
        }

        return picture;
    }

    @Override
    public void writePicture(MultipartFile file, String picturePath) {

        Path path = Paths.get(picturePath);

        try {
            Files.write(path, file.getBytes());
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
