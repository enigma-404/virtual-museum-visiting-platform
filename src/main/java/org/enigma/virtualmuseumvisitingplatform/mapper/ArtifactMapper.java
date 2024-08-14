package org.enigma.virtualmuseumvisitingplatform.mapper;

import org.enigma.virtualmuseumvisitingplatform.dto.request.artifact.ArtifactRequestDTO;
import org.enigma.virtualmuseumvisitingplatform.dto.response.artifact.ArtifactResponseDTO;
import org.enigma.virtualmuseumvisitingplatform.entity.Artifact;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArtifactMapper {
    Artifact artifactRequestDtoToArtifact(ArtifactRequestDTO dto);
    ArtifactResponseDTO artifactToArtifactResponseDTO(Artifact artifact);
}
