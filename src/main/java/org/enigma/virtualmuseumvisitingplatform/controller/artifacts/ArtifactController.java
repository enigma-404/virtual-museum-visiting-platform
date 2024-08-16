package org.enigma.virtualmuseumvisitingplatform.controller.artifacts;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.enigma.virtualmuseumvisitingplatform.core.utilities.result.DataResult;
import org.enigma.virtualmuseumvisitingplatform.core.utilities.result.Result;
import org.enigma.virtualmuseumvisitingplatform.dto.request.artifact.ArtifactSaveRequestDTO;
import org.enigma.virtualmuseumvisitingplatform.dto.request.artifact.ArtifactUpdateRequestDTO;
import org.enigma.virtualmuseumvisitingplatform.dto.response.artifact.ArtifactResponseDTO;
import org.enigma.virtualmuseumvisitingplatform.service.abstracts.artifacts.IArtifactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artifacts")
@RequiredArgsConstructor
public class ArtifactController {

    private final IArtifactService artifactService;

    @GetMapping
    public ResponseEntity<DataResult<ArtifactResponseDTO>> findById(@RequestParam long id) {
        return ResponseEntity.ok(artifactService.findById(id));
    }

    @GetMapping("/findAll")
    public ResponseEntity<DataResult<List<ArtifactResponseDTO>>> findAll(@RequestParam int pageNumber, @RequestParam int pageSize) {
        return ResponseEntity.ok(artifactService.findAllByPageNumberAndPageSize(pageNumber, pageSize));
    }

    @PostMapping
    public ResponseEntity<Result> save(@RequestBody @Valid ArtifactSaveRequestDTO artifactSaveRequestDTO) {
        return ResponseEntity.ok(this.artifactService.saveArtifact(artifactSaveRequestDTO));
    }

    @PutMapping
    public ResponseEntity<Result> update(@RequestParam long id, @RequestBody @Valid ArtifactUpdateRequestDTO artifactUpdateRequestDTO) {
        return ResponseEntity.ok(this.artifactService.updateArtifact(id ,artifactUpdateRequestDTO));
    }
}
