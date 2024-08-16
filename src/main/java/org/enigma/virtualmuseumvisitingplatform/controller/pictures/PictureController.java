package org.enigma.virtualmuseumvisitingplatform.controller.pictures;

import lombok.RequiredArgsConstructor;
import org.enigma.virtualmuseumvisitingplatform.service.abstracts.pictures.IPictureService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/picture")
@RequiredArgsConstructor
public class PictureController {

    private final IPictureService pictureService;


    @GetMapping("/{fileName}")
    public ResponseEntity<?> getPicture(@PathVariable String fileName){
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(this.pictureService.getPicture(fileName));
    }


}
