package org.enigma.virtualmuseumvisitingplatform.mapper;

import org.enigma.virtualmuseumvisitingplatform.dto.request.museum.MuseumRequestDTO;
import org.enigma.virtualmuseumvisitingplatform.dto.response.museum.MuseumResponseDTO;
import org.enigma.virtualmuseumvisitingplatform.entity.Museum;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MuseumMapper {
    Museum museumRequestDtoToMuseum(MuseumRequestDTO dto);
    MuseumResponseDTO museumToMuseumResponseDTO(Museum museum);
}
