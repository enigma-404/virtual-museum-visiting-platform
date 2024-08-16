package org.enigma.virtualmuseumvisitingplatform.mapper;

import org.enigma.virtualmuseumvisitingplatform.dto.request.artifact.ArtifactSaveRequestDTO;
import org.enigma.virtualmuseumvisitingplatform.dto.request.artifact.ArtifactUpdateRequestDTO;
import org.enigma.virtualmuseumvisitingplatform.dto.response.artifact.ArtifactResponseDTO;
import org.enigma.virtualmuseumvisitingplatform.entity.artifacts.Artifact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ArtifactMapperTest {

    @InjectMocks
    private ArtifactMapper artifactMapper = Mappers.getMapper(ArtifactMapper.class);

    private ArtifactSaveRequestDTO artifactSaveRequestDTO;
    private ArtifactUpdateRequestDTO artifactUpdateRequestDTO;
    private Artifact artifact;
    private ArtifactResponseDTO artifactResponseDTO;

    @BeforeEach
    public void setUp() {
        artifactSaveRequestDTO = new ArtifactSaveRequestDTO();
        artifactSaveRequestDTO.setName("Artifact Name");
        artifactSaveRequestDTO.setDescription("Artifact Description");
        artifactSaveRequestDTO.setLocation("Artifact Location");
        artifactSaveRequestDTO.setMuseumId(1L);

        artifactUpdateRequestDTO = new ArtifactUpdateRequestDTO();
        artifactUpdateRequestDTO.setName("Updated Name");
        artifactUpdateRequestDTO.setDescription("Updated Description");
        artifactUpdateRequestDTO.setLocation("Updated Location");

        artifact = new Artifact();
        artifact.setId(1L);
        artifact.setName("Artifact Name");
        artifact.setDescription("Artifact Description");
        artifact.setLocation("Artifact Location");
        artifact.setLikeCount(10);
        artifact.setDislikeCount(2);

        artifactResponseDTO = new ArtifactResponseDTO();
        artifactResponseDTO.setId(1L);
        artifactResponseDTO.setName("Artifact Name");
        artifactResponseDTO.setDescription("Artifact Description");
        artifactResponseDTO.setLocation("Artifact Location");
        artifactResponseDTO.setLikeCount(10);
        artifactResponseDTO.setDislikeCount(2);
    }

    @Test
    public void testArtifactRequestDtoToArtifactWhenValidDtoThenReturnArtifact() {
        // Act
        Artifact result = artifactMapper.artifactRequestDtoToArtifact(artifactSaveRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(artifactSaveRequestDTO.getName(), result.getName());
        assertEquals(artifactSaveRequestDTO.getDescription(), result.getDescription());
        assertEquals(artifactSaveRequestDTO.getLocation(), result.getLocation());
        assertEquals(artifactSaveRequestDTO.getMuseumId(), result.getMuseum().getId());
    }

    @Test
    public void testArtifactToArtifactResponseDTOWhenValidEntityThenReturnDto() {
        // Act
        ArtifactResponseDTO result = artifactMapper.artifactToArtifactResponseDTO(artifact);

        // Assert
        assertNotNull(result);
        assertEquals(artifact.getId(), result.getId());
        assertEquals(artifact.getName(), result.getName());
        assertEquals(artifact.getDescription(), result.getDescription());
        assertEquals(artifact.getLocation(), result.getLocation());
        assertEquals(artifact.getLikeCount(), result.getLikeCount());
        assertEquals(artifact.getDislikeCount(), result.getDislikeCount());
    }

    @Test
    public void testUpdateArtifactWhenValidDtoThenEntityUpdated() {
        // Act
        artifactMapper.updateArtifact(artifact, artifactUpdateRequestDTO);

        // Assert
        assertEquals(artifactUpdateRequestDTO.getName(), artifact.getName());
        assertEquals(artifactUpdateRequestDTO.getDescription(), artifact.getDescription());
        assertEquals(artifactUpdateRequestDTO.getLocation(), artifact.getLocation());
    }

    @Test
    public void testArtifactsToArtifactResponseDTOWhenValidEntitiesThenReturnDtos() {
        // Arrange
        Artifact artifact1 = new Artifact();
        artifact1.setId(1L);
        artifact1.setName("Artifact Name 1");
        artifact1.setDescription("Artifact Description 1");
        artifact1.setLocation("Artifact Location 1");
        artifact1.setLikeCount(5);
        artifact1.setDislikeCount(1);

        Artifact artifact2 = new Artifact();
        artifact2.setId(2L);
        artifact2.setName("Artifact Name 2");
        artifact2.setDescription("Artifact Description 2");
        artifact2.setLocation("Artifact Location 2");
        artifact2.setLikeCount(15);
        artifact2.setDislikeCount(3);

        List<Artifact> artifacts = Arrays.asList(artifact1, artifact2);

        // Act
        List<ArtifactResponseDTO> result = artifactMapper.artifactsToArtifactResponseDTO(artifacts);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        ArtifactResponseDTO dto1 = result.get(0);
        assertEquals(artifact1.getId(), dto1.getId());
        assertEquals(artifact1.getName(), dto1.getName());
        assertEquals(artifact1.getDescription(), dto1.getDescription());
        assertEquals(artifact1.getLocation(), dto1.getLocation());
        assertEquals(artifact1.getLikeCount(), dto1.getLikeCount());
        assertEquals(artifact1.getDislikeCount(), dto1.getDislikeCount());

        ArtifactResponseDTO dto2 = result.get(1);
        assertEquals(artifact2.getId(), dto2.getId());
        assertEquals(artifact2.getName(), dto2.getName());
        assertEquals(artifact2.getDescription(), dto2.getDescription());
        assertEquals(artifact2.getLocation(), dto2.getLocation());
        assertEquals(artifact2.getLikeCount(), dto2.getLikeCount());
        assertEquals(artifact2.getDislikeCount(), dto2.getDislikeCount());
    }
}
