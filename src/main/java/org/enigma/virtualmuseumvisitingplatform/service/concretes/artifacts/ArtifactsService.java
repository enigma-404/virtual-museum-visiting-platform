package org.enigma.virtualmuseumvisitingplatform.service.concretes.artifacts;

import lombok.RequiredArgsConstructor;
import org.enigma.virtualmuseumvisitingplatform.core.result.DataResult;
import org.enigma.virtualmuseumvisitingplatform.core.result.Result;
import org.enigma.virtualmuseumvisitingplatform.core.result.SuccessDataResult;
import org.enigma.virtualmuseumvisitingplatform.core.result.SuccessResult;
import org.enigma.virtualmuseumvisitingplatform.dto.request.artifact.ArtifactSaveRequestDTO;
import org.enigma.virtualmuseumvisitingplatform.dto.request.artifact.ArtifactUpdateRequestDTO;
import org.enigma.virtualmuseumvisitingplatform.dto.response.artifact.ArtifactResponseDTO;
import org.enigma.virtualmuseumvisitingplatform.entity.Artifact;
import org.enigma.virtualmuseumvisitingplatform.exceptions.cutomExceptions.ArtifactNotFoundException;
import org.enigma.virtualmuseumvisitingplatform.mapper.ArtifactMapper;
import org.enigma.virtualmuseumvisitingplatform.repository.artifact.ArtifactRepository;
import org.enigma.virtualmuseumvisitingplatform.service.abstracts.artifacts.IArtifactService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class ArtifactsService implements IArtifactService {


    private final ArtifactRepository artifactRepository;
    private final ArtifactMapper artifactMapper;

    @Override
    public Result saveArtifact(ArtifactSaveRequestDTO artifactSaveRequestDTO) {
        // map dto to entity

        Artifact artifact = this.artifactMapper.artifactRequestDtoToArtifact(artifactSaveRequestDTO);
        this.artifactRepository.save(artifact);

        return new SuccessResult("Successfully saved artifact");
    }

    @Override
    public Result updateArtifact(Long id, ArtifactUpdateRequestDTO artifactUpdateRequestDTO) {

        Artifact artifact = this.artifactRepository.findById(id).orElseThrow(() -> new ArtifactNotFoundException("Artifact not found with id: " + id));

        // map values to entity
        this.artifactMapper.updateArtifact(artifact, artifactUpdateRequestDTO);

        // update entity
        this.artifactRepository.save(artifact);
        return new SuccessResult("Successfully updated artifact");
    }

    @Override
    public DataResult<List<ArtifactResponseDTO>> findAllByPageNumberAndPageSize(int pageNumber, int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Artifact> artifact = this.artifactRepository.findAll(pageable).getContent();

        // map artifact to dto
        List<ArtifactResponseDTO> artifactResponseDTOS = this.artifactMapper.artifactsToArtifactResponseDTO(artifact);
        return new SuccessDataResult<>(artifactResponseDTOS, "Successfully retrieved all artifacts");
    }

    @Override
    public DataResult<ArtifactResponseDTO> findById(long id) {
        Artifact artifact = this.artifactRepository.findById(id).orElseThrow(() -> new ArtifactNotFoundException("Artifact not found with id: " + id));

        // map to dto
        ArtifactResponseDTO artifactResponseDTO = this.artifactMapper.artifactToArtifactResponseDTO(artifact);
        return new SuccessDataResult<>(artifactResponseDTO, "Successfully retrieved artifact");
    }
}
