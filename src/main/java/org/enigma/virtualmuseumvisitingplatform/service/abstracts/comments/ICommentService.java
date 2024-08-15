package org.enigma.virtualmuseumvisitingplatform.service.abstracts.comments;

import org.enigma.virtualmuseumvisitingplatform.core.result.DataResult;
import org.enigma.virtualmuseumvisitingplatform.core.result.Result;
import org.enigma.virtualmuseumvisitingplatform.dto.request.comment.CommentSaveRequestDTO;
import org.enigma.virtualmuseumvisitingplatform.dto.response.comment.CommentResponseDTO;

import java.util.List;

public interface ICommentService {
    Result saveComment(CommentSaveRequestDTO commentSaveRequestDTO);
    Result deleteComment(Long id);
    Result updateComment();
    DataResult<List<CommentResponseDTO>> findByArtifactId(Long id);
}
