package org.enigma.virtualmuseumvisitingplatform.mapper;

import org.enigma.virtualmuseumvisitingplatform.dto.request.museum.MuseumSaveRequestDTO;
import org.enigma.virtualmuseumvisitingplatform.dto.request.museum.MuseumUpdateRequestDTO;
import org.enigma.virtualmuseumvisitingplatform.dto.response.museum.MuseumResponseDTO;
import org.enigma.virtualmuseumvisitingplatform.entity.Museum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MuseumMapperTest {

    private MuseumMapper museumMapper;

    @BeforeEach
    void setUp() {
        museumMapper = Mappers.getMapper(MuseumMapper.class);
    }

    @Test
    void testMuseumRequestDtoToMuseum() {
        MuseumSaveRequestDTO dto = new MuseumSaveRequestDTO();
        dto.setName("National Museum");
        dto.setDescription("A museum description");

        Museum museum = museumMapper.museumRequestDtoToMuseum(dto);

        assertNotNull(museum);
        assertEquals("National Museum", museum.getName());
        assertEquals("A museum description", museum.getDescription());
    }

    @Test
    void testUpdateMuseumWithMuseumUpdateRequestDTO() {
        MuseumUpdateRequestDTO updateDTO = new MuseumUpdateRequestDTO();
        updateDTO.setName("Updated Museum Name");
        updateDTO.setDescription("Updated Description");

        Museum museum = new Museum();
        museum.setName("Original Name");
        museum.setDescription("Original Description");

        museumMapper.updateMuseumWithMuseumUpdateRequestDTO(museum, updateDTO);

        assertNotNull(museum);
        assertEquals("Updated Museum Name", museum.getName());
        assertEquals("Updated Description", museum.getDescription());
    }

    @Test
    void testMuseumToMuseumResponseDTO() {
        Museum museum = new Museum();
        museum.setId(1L);
        museum.setName("Museum Name");
        museum.setDescription("Museum Description");

        MuseumResponseDTO responseDTO = museumMapper.museumToMuseumResponseDTO(museum);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getId());
        assertEquals("Museum Name", responseDTO.getName());
        assertEquals("Museum Description", responseDTO.getDescription());
    }

    @Test
    void testMuseumToMuseumResponseDTOList() {
        Museum museum1 = new Museum();
        museum1.setId(1L);
        museum1.setName("Museum One");
        museum1.setDescription("Description One");

        Museum museum2 = new Museum();
        museum2.setId(2L);
        museum2.setName("Museum Two");
        museum2.setDescription("Description Two");

        List<Museum> museums = Arrays.asList(museum1, museum2);

        List<MuseumResponseDTO> dtos = museumMapper.museumToMuseumResponseDTOList(museums);

        assertNotNull(dtos);
        assertEquals(2, dtos.size());

        assertEquals(1L, dtos.get(0).getId());
        assertEquals("Museum One", dtos.get(0).getName());
        assertEquals("Description One", dtos.get(0).getDescription());

        assertEquals(2L, dtos.get(1).getId());
        assertEquals("Museum Two", dtos.get(1).getName());
        assertEquals("Description Two", dtos.get(1).getDescription());
    }
}
