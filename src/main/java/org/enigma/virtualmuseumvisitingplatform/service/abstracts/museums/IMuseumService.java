package org.enigma.virtualmuseumvisitingplatform.service.abstracts.museums;

import org.enigma.virtualmuseumvisitingplatform.core.result.DataResult;
import org.enigma.virtualmuseumvisitingplatform.core.result.Result;
import org.enigma.virtualmuseumvisitingplatform.dto.request.museum.MuseumSaveRequestDTO;
import org.enigma.virtualmuseumvisitingplatform.dto.request.museum.MuseumUpdateRequestDTO;
import org.enigma.virtualmuseumvisitingplatform.dto.response.museum.MuseumResponseDTO;

import java.util.List;

public interface IMuseumService {
    Result saveMuseum(MuseumSaveRequestDTO museumSaveRequestDTO);
    Result updateMuseum(Long id, MuseumUpdateRequestDTO museumUpdateRequestDTO);
    DataResult<List<MuseumResponseDTO>> findAllByPageNumberAndPageSize(int pageNumber, int pageSize);
    DataResult<MuseumResponseDTO> findMuseumById(long id);
}
