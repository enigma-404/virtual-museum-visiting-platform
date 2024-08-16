package org.enigma.virtualmuseumvisitingplatform.mapper;

import org.enigma.virtualmuseumvisitingplatform.dto.request.artifact.ArtifactSaveRequestDTO;
import org.enigma.virtualmuseumvisitingplatform.dto.request.artifact.ArtifactUpdateRequestDTO;
import org.enigma.virtualmuseumvisitingplatform.dto.response.artifact.ArtifactResponseDTO;
import org.enigma.virtualmuseumvisitingplatform.entity.artifacts.Artifact;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ArtifactMapper {

    @Mapping(target = "museum.id", source = "museumId")
    Artifact artifactRequestDtoToArtifact(ArtifactSaveRequestDTO dto);
    ArtifactResponseDTO artifactToArtifactResponseDTO(Artifact artifact);
    void updateArtifact(@MappingTarget Artifact artifact, ArtifactUpdateRequestDTO updateRequestDTO);
    List<ArtifactResponseDTO> artifactsToArtifactResponseDTO(List<Artifact> artifacts);
}
