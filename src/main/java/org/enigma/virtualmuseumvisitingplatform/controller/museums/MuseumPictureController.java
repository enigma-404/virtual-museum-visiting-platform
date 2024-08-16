package org.enigma.virtualmuseumvisitingplatform.controller.museums;

import lombok.RequiredArgsConstructor;
import org.enigma.virtualmuseumvisitingplatform.core.utilities.result.DataResult;
import org.enigma.virtualmuseumvisitingplatform.core.utilities.result.Result;
import org.enigma.virtualmuseumvisitingplatform.service.abstracts.museums.IMuseumPictureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/museumPictures")
@RequiredArgsConstructor
public class MuseumPictureController {

    private final IMuseumPictureService museumPictureService;

    @PostMapping
    public ResponseEntity<Result> savePicture(MultipartFile file, long museumId) {
        return ResponseEntity.ok(museumPictureService.savePicture(museumId, file));
    }

    @GetMapping("/findByMuseumId")
    public ResponseEntity<DataResult<List<String>>> findByMuseumId(long museumId) {
        return ResponseEntity.ok(this.museumPictureService.findPictureByMuseumId(museumId));
    }
}
