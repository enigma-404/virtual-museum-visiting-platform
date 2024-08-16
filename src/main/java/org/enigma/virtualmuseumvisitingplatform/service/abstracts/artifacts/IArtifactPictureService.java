package org.enigma.virtualmuseumvisitingplatform.service.abstracts.artifacts;

import org.enigma.virtualmuseumvisitingplatform.core.utilities.result.DataResult;
import org.enigma.virtualmuseumvisitingplatform.core.utilities.result.Result;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IArtifactPictureService {
    Result savePicture(Long artifactId, MultipartFile file);
    DataResult<List<String>> findPictureByArtifactId(Long artifactId);
}
