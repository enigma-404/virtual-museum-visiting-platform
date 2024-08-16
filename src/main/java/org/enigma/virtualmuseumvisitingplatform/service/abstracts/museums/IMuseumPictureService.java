package org.enigma.virtualmuseumvisitingplatform.service.abstracts.museums;

import org.enigma.virtualmuseumvisitingplatform.core.utilities.result.DataResult;
import org.enigma.virtualmuseumvisitingplatform.core.utilities.result.Result;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IMuseumPictureService {
    Result savePicture(Long museumId, MultipartFile file);
    DataResult<List<String>> findPictureByMuseumId(Long museumId);
}
