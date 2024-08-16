package org.enigma.virtualmuseumvisitingplatform.service.concretes.artifacts;

import lombok.RequiredArgsConstructor;
import org.enigma.virtualmuseumvisitingplatform.core.utilities.pictures.BaseReadAndWritePicture;
import org.enigma.virtualmuseumvisitingplatform.core.utilities.result.DataResult;
import org.enigma.virtualmuseumvisitingplatform.core.utilities.result.Result;
import org.enigma.virtualmuseumvisitingplatform.core.utilities.result.SuccessDataResult;
import org.enigma.virtualmuseumvisitingplatform.core.utilities.result.SuccessResult;
import org.enigma.virtualmuseumvisitingplatform.entity.artifacts.Artifact;
import org.enigma.virtualmuseumvisitingplatform.entity.artifacts.ArtifactPicture;
import org.enigma.virtualmuseumvisitingplatform.entity.museums.Museum;
import org.enigma.virtualmuseumvisitingplatform.entity.museums.MuseumPicture;
import org.enigma.virtualmuseumvisitingplatform.repository.artifact.ArtifactPictureRepository;
import org.enigma.virtualmuseumvisitingplatform.repository.artifact.ArtifactRepository;
import org.enigma.virtualmuseumvisitingplatform.service.abstracts.artifacts.IArtifactPictureService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtifactPictureService implements IArtifactPictureService {

    private final ArtifactPictureRepository artifactPictureRepository;
    private final BaseReadAndWritePicture baseReadAndWritePicture;

    @Value("${picture.url}")
    private String pictureUrl;
    @Value("${picture.path}")
    private String picturePath;


    @Override
    public Result savePicture(Long artifactId, MultipartFile file) {
        ArtifactPicture artifactPicture = new ArtifactPicture();
        String pictureName = artifactPicture + "_" + LocalDateTime.now();

        artifactPicture.setName(pictureName);
        artifactPicture.setPath(picturePath + pictureName);
        artifactPicture.setUrl(pictureUrl + pictureName);
        artifactPicture.setCreatedAt(LocalDateTime.now());
        artifactPicture.setArtifact(Artifact.builder().id(artifactId).build());

        this.artifactPictureRepository.save(artifactPicture);

        this.baseReadAndWritePicture.writePicture(file, picturePath + pictureName);
        return new SuccessResult("Artifact Picture Saved");
    }

    @Override
    public DataResult<List<String>> findPictureByArtifactId(Long artifactId) {
        return new SuccessDataResult<>(this.artifactPictureRepository.findUrlsByArtifactId(artifactId), "Artifact Picture List");
    }
}
