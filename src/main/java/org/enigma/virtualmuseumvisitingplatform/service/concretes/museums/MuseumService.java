package org.enigma.virtualmuseumvisitingplatform.service.concretes.museums;

import lombok.RequiredArgsConstructor;
import org.enigma.virtualmuseumvisitingplatform.core.result.DataResult;
import org.enigma.virtualmuseumvisitingplatform.core.result.Result;
import org.enigma.virtualmuseumvisitingplatform.core.result.SuccessDataResult;
import org.enigma.virtualmuseumvisitingplatform.core.result.SuccessResult;
import org.enigma.virtualmuseumvisitingplatform.dto.request.museum.MuseumSaveRequestDTO;
import org.enigma.virtualmuseumvisitingplatform.dto.request.museum.MuseumUpdateRequestDTO;
import org.enigma.virtualmuseumvisitingplatform.dto.response.museum.MuseumResponseDTO;
import org.enigma.virtualmuseumvisitingplatform.entity.Museum;
import org.enigma.virtualmuseumvisitingplatform.exceptions.cutomExceptions.MuseumNotFoundException;
import org.enigma.virtualmuseumvisitingplatform.mapper.MuseumMapper;
import org.enigma.virtualmuseumvisitingplatform.repository.museum.MuseumRepository;
import org.enigma.virtualmuseumvisitingplatform.service.abstracts.museums.IMuseumService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MuseumService implements IMuseumService {

    private final MuseumRepository museumRepository;
    private final MuseumMapper museumMapper;

    @Override
    public Result updateMuseum(Long id,MuseumUpdateRequestDTO museumUpdateRequestDTO) {
        Museum museum = this.museumRepository.findById(id).orElseThrow(() -> new MuseumNotFoundException("Museum not found with id: " + id));

        // update values with mapper
        this.museumMapper.updateMuseumWithMuseumUpdateRequestDTO(museum, museumUpdateRequestDTO);

        // save museum
        this.museumRepository.save(museum);
        return new SuccessResult("Successfully updated Museum with id: " + id);
    }

    @Override
    public DataResult<List<MuseumResponseDTO>> findAllByPageNumberAndPageSize(int pageNumber, int pageSize) {

        // Find all museums by pageable
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Museum> museums = this.museumRepository.findAll(pageable).getContent();

        // map museums to museums dto
        List<MuseumResponseDTO> museumResponseDTOS = this.museumMapper.museumToMuseumResponseDTOList(museums);
        return new SuccessDataResult<>(museumResponseDTOS, "Successfully retrieved all museums");
    }

    @Override
    public DataResult<MuseumResponseDTO> findMuseumById(long id) {
        Museum museum = this.museumRepository.findById(id).orElseThrow(() -> new MuseumNotFoundException("Museum not found with id: " + id));

        // map to dto
        MuseumResponseDTO museumResponseDTO = this.museumMapper.museumToMuseumResponseDTO(museum);
        return new SuccessDataResult<>(museumResponseDTO, "Successfully retrieved museum");
    }

    @Override
    public Result saveMuseum(MuseumSaveRequestDTO museumSaveRequestDTO) {
        // map dto to entity
        Museum museum = this.museumMapper.museumRequestDtoToMuseum(museumSaveRequestDTO);

        // save museum
        this.museumRepository.save(museum);
        return new SuccessResult("Successfully saved museum");
    }
}
