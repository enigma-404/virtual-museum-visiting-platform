package org.enigma.virtualmuseumvisitingplatform.mapper;

import org.enigma.virtualmuseumvisitingplatform.dto.request.museum.MuseumSaveRequestDTO;
import org.enigma.virtualmuseumvisitingplatform.dto.request.museum.MuseumUpdateRequestDTO;
import org.enigma.virtualmuseumvisitingplatform.dto.response.museum.MuseumResponseDTO;
import org.enigma.virtualmuseumvisitingplatform.entity.Museum;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface MuseumMapper {
    Museum museumRequestDtoToMuseum(MuseumSaveRequestDTO dto);
    void updateMuseumWithMuseumUpdateRequestDTO(@MappingTarget Museum museum, MuseumUpdateRequestDTO museumUpdateRequestDTO);
    MuseumResponseDTO museumToMuseumResponseDTO(Museum museum);
    List<MuseumResponseDTO> museumToMuseumResponseDTOList(List<Museum> museums);
}
