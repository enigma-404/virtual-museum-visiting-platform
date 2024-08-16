package org.enigma.virtualmuseumvisitingplatform.service.concretes.artifacts;

import org.enigma.virtualmuseumvisitingplatform.core.utilities.result.DataResult;
import org.enigma.virtualmuseumvisitingplatform.core.utilities.result.Result;
import org.enigma.virtualmuseumvisitingplatform.dto.request.artifact.ArtifactSaveRequestDTO;
import org.enigma.virtualmuseumvisitingplatform.dto.request.artifact.ArtifactUpdateRequestDTO;
import org.enigma.virtualmuseumvisitingplatform.dto.response.artifact.ArtifactResponseDTO;
import org.enigma.virtualmuseumvisitingplatform.entity.artifacts.Artifact;
import org.enigma.virtualmuseumvisitingplatform.exceptions.cutomExceptions.ArtifactNotFoundException;
import org.enigma.virtualmuseumvisitingplatform.mapper.ArtifactMapper;
import org.enigma.virtualmuseumvisitingplatform.repository.artifact.ArtifactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

class ArtifactsServiceTest {

    @InjectMocks
    private ArtifactsService artifactsService;

    @Mock
    private ArtifactRepository artifactRepository;

    @Mock
    private ArtifactMapper artifactMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveArtifact() {
        // Arrange
        ArtifactSaveRequestDTO saveRequestDTO = new ArtifactSaveRequestDTO();
        Artifact artifact = new Artifact();

        BDDMockito.given(artifactMapper.artifactRequestDtoToArtifact(any(ArtifactSaveRequestDTO.class))).willReturn(artifact);
        BDDMockito.given(artifactRepository.save(any(Artifact.class))).willReturn(artifact);

        // Act
        Result result = artifactsService.saveArtifact(saveRequestDTO);

        // Assert
        assertTrue(result.isSuccess());
        assertEquals("Successfully saved artifact", result.getMessage());
    }

    @Test
    void testUpdateArtifact() {
        // Arrange
        ArtifactUpdateRequestDTO updateRequestDTO = new ArtifactUpdateRequestDTO();
        Artifact artifact = new Artifact();
        artifact.setId(1L);

        BDDMockito.given(artifactRepository.findById(anyLong())).willReturn(Optional.of(artifact));
        BDDMockito.doNothing().when(artifactMapper).updateArtifact(any(Artifact.class), any(ArtifactUpdateRequestDTO.class));
        BDDMockito.given(artifactRepository.save(any(Artifact.class))).willReturn(artifact);

        // Act
        Result result = artifactsService.updateArtifact(1L, updateRequestDTO);

        // Assert
        assertTrue(result.isSuccess());
        assertEquals("Successfully updated artifact", result.getMessage());
    }

    @Test
    void testFindAllByPageNumberAndPageSize() {
        // Arrange
        Artifact artifact = new Artifact();
        Page<Artifact> artifactPage = new PageImpl<>(Collections.singletonList(artifact));
        ArtifactResponseDTO responseDTO = new ArtifactResponseDTO();

        BDDMockito.given(artifactRepository.findAll(any(Pageable.class))).willReturn(artifactPage);
        BDDMockito.given(artifactMapper.artifactsToArtifactResponseDTO(any(List.class))).willReturn(Collections.singletonList(responseDTO));

        // Act
        DataResult<List<ArtifactResponseDTO>> result = artifactsService.findAllByPageNumberAndPageSize(0, 10);

        // Assert
        assertTrue(result.isSuccess());
        assertEquals(1, result.getData().size());
        assertEquals("Successfully retrieved all artifacts", result.getMessage());
    }

    @Test
    void testFindById() {
        // Arrange
        Artifact artifact = new Artifact();
        artifact.setId(1L);
        ArtifactResponseDTO responseDTO = new ArtifactResponseDTO();

        BDDMockito.given(artifactRepository.findById(anyLong())).willReturn(Optional.of(artifact));
        BDDMockito.given(artifactMapper.artifactToArtifactResponseDTO(any(Artifact.class))).willReturn(responseDTO);

        // Act
        DataResult<ArtifactResponseDTO> result = artifactsService.findById(1L);

        // Assert
        assertTrue(result.isSuccess());
        assertNotNull(result.getData());
        assertEquals("Successfully retrieved artifact", result.getMessage());
    }

    @Test
    void testFindByIdThrowsExceptionWhenNotFound() {
        // Arrange
        BDDMockito.given(artifactRepository.findById(anyLong())).willReturn(Optional.empty());

        // Act & Assert
        assertThrows(ArtifactNotFoundException.class, () -> artifactsService.findById(1L));
    }
}
