package org.enigma.virtualmuseumvisitingplatform.controller.artifacts;

import lombok.RequiredArgsConstructor;
import org.enigma.virtualmuseumvisitingplatform.core.utilities.result.DataResult;
import org.enigma.virtualmuseumvisitingplatform.core.utilities.result.Result;
import org.enigma.virtualmuseumvisitingplatform.service.abstracts.artifacts.IArtifactPictureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/artifactPictures")
@RequiredArgsConstructor
public class ArtifactPictureController {

    private final IArtifactPictureService iArtifactPictureService;

    @GetMapping("/findByArtifactId")
    public ResponseEntity<DataResult<List<String>>> findByArtifactId(long artifactId) {
        return ResponseEntity.ok(this.iArtifactPictureService.findPictureByArtifactId(artifactId));
    }

    @PostMapping
    public ResponseEntity<Result> save(long artifactId, MultipartFile file) {
        return ResponseEntity.ok(this.iArtifactPictureService.savePicture(artifactId, file));
    }
}
