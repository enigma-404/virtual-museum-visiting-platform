package org.enigma.virtualmuseumvisitingplatform.controller.museums;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.enigma.virtualmuseumvisitingplatform.core.result.DataResult;
import org.enigma.virtualmuseumvisitingplatform.core.result.Result;
import org.enigma.virtualmuseumvisitingplatform.dto.request.museum.MuseumSaveRequestDTO;
import org.enigma.virtualmuseumvisitingplatform.dto.request.museum.MuseumUpdateRequestDTO;
import org.enigma.virtualmuseumvisitingplatform.dto.response.museum.MuseumResponseDTO;
import org.enigma.virtualmuseumvisitingplatform.service.abstracts.museums.IMuseumService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/museums")
@RequiredArgsConstructor
public class MuseumController {

    private final IMuseumService museumService;

    @GetMapping
    public ResponseEntity<DataResult<MuseumResponseDTO>> findById(@RequestParam long id) {
        return ResponseEntity.ok(this.museumService.findMuseumById(id));
    }

    @GetMapping("/findAll")
    public ResponseEntity<DataResult<List<MuseumResponseDTO>>> findAll(int pageNumber, int pageSize) {
        return ResponseEntity.ok(this.museumService.findAllByPageNumberAndPageSize(pageNumber, pageSize));
    }

    @PostMapping
    public ResponseEntity<Result> save(@RequestBody @Valid MuseumSaveRequestDTO museumSaveRequestDTO){
        return ResponseEntity.ok(this.museumService.saveMuseum(museumSaveRequestDTO));
    }

    @PutMapping
    public ResponseEntity<Result> update(@RequestParam Long id, @RequestBody @Valid MuseumUpdateRequestDTO museumUpdateRequestDTO){
        return ResponseEntity.ok(this.museumService.updateMuseum(id, museumUpdateRequestDTO));
    }

}
