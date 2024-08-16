package org.enigma.virtualmuseumvisitingplatform.service.concretes.museums;

import org.enigma.virtualmuseumvisitingplatform.core.result.DataResult;
import org.enigma.virtualmuseumvisitingplatform.core.result.Result;
import org.enigma.virtualmuseumvisitingplatform.dto.request.museum.MuseumSaveRequestDTO;
import org.enigma.virtualmuseumvisitingplatform.dto.request.museum.MuseumUpdateRequestDTO;
import org.enigma.virtualmuseumvisitingplatform.dto.response.museum.MuseumResponseDTO;
import org.enigma.virtualmuseumvisitingplatform.entity.Museum;
import org.enigma.virtualmuseumvisitingplatform.exceptions.cutomExceptions.MuseumNotFoundException;
import org.enigma.virtualmuseumvisitingplatform.mapper.MuseumMapper;
import org.enigma.virtualmuseumvisitingplatform.repository.museum.MuseumRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MuseumServiceTest {

    private MuseumService museumService;
    private MuseumRepository museumRepository;
    private MuseumMapper museumMapper;

    @BeforeEach
    void setUp() {
        museumRepository = mock(MuseumRepository.class);
        museumMapper = mock(MuseumMapper.class);
        museumService = new MuseumService(museumRepository, museumMapper);
    }

    @Test
    void testSaveMuseum_Success() {
        MuseumSaveRequestDTO museumSaveRequestDTO = new MuseumSaveRequestDTO();
        Museum museum = new Museum();

        when(museumMapper.museumRequestDtoToMuseum(any(MuseumSaveRequestDTO.class))).thenReturn(museum);
        when(museumRepository.save(any(Museum.class))).thenReturn(museum);

        Result result = museumService.saveMuseum(museumSaveRequestDTO);

        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals("Successfully saved museum", result.getMessage());

        verify(museumRepository, times(1)).save(museum);
    }

    @Test
    void testFindMuseumById_Success() {
        long museumId = 1L;
        Museum museum = new Museum();
        MuseumResponseDTO museumResponseDTO = new MuseumResponseDTO();

        when(museumRepository.findById(museumId)).thenReturn(Optional.of(museum));
        when(museumMapper.museumToMuseumResponseDTO(museum)).thenReturn(museumResponseDTO);

        DataResult<MuseumResponseDTO> result = museumService.findMuseumById(museumId);

        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals(museumResponseDTO, result.getData());

        verify(museumRepository, times(1)).findById(museumId);
    }

    @Test
    void testFindMuseumById_NotFound() {
        long museumId = 1L;

        when(museumRepository.findById(museumId)).thenReturn(Optional.empty());

        assertThrows(MuseumNotFoundException.class, () -> museumService.findMuseumById(museumId));

        verify(museumRepository, times(1)).findById(museumId);
    }

    @Test
    void testFindAllByPageNumberAndPageSize_Success() {
        int pageNumber = 0;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Museum museum = new Museum();
        MuseumResponseDTO museumResponseDTO = new MuseumResponseDTO();

        Page<Museum> page = new PageImpl<>(Collections.singletonList(museum), pageable, 1);
        when(museumRepository.findAll(pageable)).thenReturn(page);
        when(museumMapper.museumToMuseumResponseDTOList(anyList())).thenReturn(Collections.singletonList(museumResponseDTO));

        DataResult<List<MuseumResponseDTO>> result = museumService.findAllByPageNumberAndPageSize(pageNumber, pageSize);

        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals(1, result.getData().size());

        verify(museumRepository, times(1)).findAll(pageable);
    }

    @Test
    void testUpdateMuseum_Success() {
        long museumId = 1L;
        Museum museum = new Museum();
        museum.setId(museumId);
        museum.setName("Name");
        museumRepository.save(museum);

        MuseumUpdateRequestDTO museumUpdateRequestDTO = new MuseumUpdateRequestDTO();
        museumUpdateRequestDTO.setName("Updated Name");

        when(museumRepository.findById(museumId)).thenReturn(Optional.of(museum));

        // Ensure the mapper modifies the museum object
        doAnswer(invocation -> {
            Museum targetMuseum = invocation.getArgument(0);
            MuseumUpdateRequestDTO sourceDTO = invocation.getArgument(1);
            targetMuseum.setName(sourceDTO.getName());
            // Simulate other modifications if necessary
            return null;
        }).when(museumMapper).updateMuseumWithMuseumUpdateRequestDTO(any(Museum.class), any(MuseumUpdateRequestDTO.class));

        Result result = museumService.updateMuseum(museumId, museumUpdateRequestDTO);

        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals("Successfully updated Museum with id: " + museumId, result.getMessage());

        verify(museumRepository, times(1)).findById(museumId);
        verify(museumRepository, times(1)).save(museum);
    }

    @Test
    void testUpdateMuseum_NotFound() {
        long museumId = 1L;
        MuseumUpdateRequestDTO museumUpdateRequestDTO = new MuseumUpdateRequestDTO();

        when(museumRepository.findById(museumId)).thenReturn(Optional.empty());

        assertThrows(MuseumNotFoundException.class, () -> museumService.updateMuseum(museumId, museumUpdateRequestDTO));

        verify(museumRepository, times(1)).findById(museumId);
    }
}
