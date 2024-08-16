package org.enigma.virtualmuseumvisitingplatform.controller.comments;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.enigma.virtualmuseumvisitingplatform.core.utilities.result.Result;
import org.enigma.virtualmuseumvisitingplatform.dto.request.comment.CommentSaveRequestDTO;
import org.enigma.virtualmuseumvisitingplatform.service.abstracts.comments.ICommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@Validated
public class CommentController {

    private final ICommentService commentService;

    @PostMapping
    public ResponseEntity<Result> saveComment(@RequestBody @Valid CommentSaveRequestDTO commentSaveRequestDTO) {
        return ResponseEntity.ok(commentService.saveComment(commentSaveRequestDTO));
    }
}
