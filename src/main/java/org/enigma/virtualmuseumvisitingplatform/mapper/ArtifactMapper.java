package org.enigma.virtualmuseumvisitingplatform.mapper;

import org.enigma.virtualmuseumvisitingplatform.dto.request.artifact.ArtifactSaveRequestDTO;
import org.enigma.virtualmuseumvisitingplatform.dto.request.artifact.ArtifactUpdateRequestDTO;
import org.enigma.virtualmuseumvisitingplatform.dto.response.artifact.ArtifactResponseDTO;
import org.enigma.virtualmuseumvisitingplatform.entity.Artifact;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ArtifactMapper {
    Artifact artifactRequestDtoToArtifact(ArtifactSaveRequestDTO dto);
    ArtifactResponseDTO artifactToArtifactResponseDTO(Artifact artifact);
    void updateArtifact(@MappingTarget Artifact artifact, ArtifactUpdateRequestDTO updateRequestDTO);
    List<ArtifactResponseDTO> artifactsToArtifactResponseDTO(List<Artifact> artifacts);
}
