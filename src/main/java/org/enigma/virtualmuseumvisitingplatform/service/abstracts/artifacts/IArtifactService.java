package org.enigma.virtualmuseumvisitingplatform.service.abstracts.artifacts;

import org.enigma.virtualmuseumvisitingplatform.core.utilities.result.DataResult;
import org.enigma.virtualmuseumvisitingplatform.core.utilities.result.Result;
import org.enigma.virtualmuseumvisitingplatform.dto.request.artifact.ArtifactSaveRequestDTO;
import org.enigma.virtualmuseumvisitingplatform.dto.request.artifact.ArtifactUpdateRequestDTO;
import org.enigma.virtualmuseumvisitingplatform.dto.response.artifact.ArtifactResponseDTO;

import java.util.List;

public interface IArtifactService {
    Result saveArtifact(ArtifactSaveRequestDTO artifactSaveRequestDTO);
    Result updateArtifact(Long id, ArtifactUpdateRequestDTO artifact);
    DataResult<List<ArtifactResponseDTO>> findAllByPageNumberAndPageSize(int pageNumber, int pageSize);
    DataResult<ArtifactResponseDTO> findById(long id);
}
