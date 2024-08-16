package org.enigma.virtualmuseumvisitingplatform.controller.user;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.enigma.virtualmuseumvisitingplatform.core.utilities.result.DataResult;
import org.enigma.virtualmuseumvisitingplatform.core.utilities.result.Result;
import org.enigma.virtualmuseumvisitingplatform.entity.artifacts.Artifact;
import org.enigma.virtualmuseumvisitingplatform.service.abstracts.users.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;


    @PostMapping("/addFavourite")
    public ResponseEntity<Result> addFavourite(long userId, long artifactId) {
        return ResponseEntity.ok(userService.addFavourite(userId, artifactId));
    }

    @GetMapping("/getFavourite")
    public ResponseEntity<DataResult<List<Artifact>>> getFavourite(long userId) {
        return ResponseEntity.ok(this.userService.getFavourites(userId));
    }

}
