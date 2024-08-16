package org.enigma.virtualmuseumvisitingplatform.service.concretes.museums;

import lombok.RequiredArgsConstructor;
import org.enigma.virtualmuseumvisitingplatform.core.utilities.pictures.BaseReadAndWritePicture;
import org.enigma.virtualmuseumvisitingplatform.core.utilities.result.DataResult;
import org.enigma.virtualmuseumvisitingplatform.core.utilities.result.Result;
import org.enigma.virtualmuseumvisitingplatform.core.utilities.result.SuccessDataResult;
import org.enigma.virtualmuseumvisitingplatform.core.utilities.result.SuccessResult;
import org.enigma.virtualmuseumvisitingplatform.entity.museums.Museum;
import org.enigma.virtualmuseumvisitingplatform.entity.museums.MuseumPicture;
import org.enigma.virtualmuseumvisitingplatform.repository.museum.MuseumPictureRepository;
import org.enigma.virtualmuseumvisitingplatform.service.abstracts.museums.IMuseumPictureService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MuseumPictureService implements IMuseumPictureService {

    private final MuseumPictureRepository museumPictureRepository;
    private final BaseReadAndWritePicture baseReadAndWritePicture;

    @Value("${picture.url}")
    private String pictureUrl;
    @Value("${picture.path}")
    private String picturePath;

    @Override
    public Result savePicture(Long museumId, MultipartFile file) {
        MuseumPicture museumPicture = new MuseumPicture();
        String pictureName = museumId + "_" + LocalDateTime.now();

        museumPicture.setName(pictureName);
        museumPicture.setPath(picturePath + pictureName);
        museumPicture.setUrl(pictureUrl + pictureName);
        museumPicture.setCreatedAt(LocalDateTime.now());
        museumPicture.setMuseum(Museum.builder().id(museumId).build());

        this.museumPictureRepository.save(museumPicture);

        this.baseReadAndWritePicture.writePicture(file, picturePath + pictureName);
        return new SuccessResult("Saved picture");
    }

    @Override
    public DataResult<List<String>> findPictureByMuseumId(Long museumId) {
        return new SuccessDataResult<>(this.museumPictureRepository.findUrlsByMuseumId(museumId), "Successfully found picture urls");
    }
}
