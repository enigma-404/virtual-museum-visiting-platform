package org.enigma.virtualmuseumvisitingplatform.service.concretes.comments;

import lombok.RequiredArgsConstructor;
import org.enigma.virtualmuseumvisitingplatform.core.components.LLamaAIAdapter;
import org.enigma.virtualmuseumvisitingplatform.core.utilities.result.DataResult;
import org.enigma.virtualmuseumvisitingplatform.core.utilities.result.Result;
import org.enigma.virtualmuseumvisitingplatform.core.utilities.result.SuccessDataResult;
import org.enigma.virtualmuseumvisitingplatform.core.utilities.result.SuccessResult;
import org.enigma.virtualmuseumvisitingplatform.dto.request.comment.CommentSaveRequestDTO;
import org.enigma.virtualmuseumvisitingplatform.dto.response.comment.CommentResponseDTO;
import org.enigma.virtualmuseumvisitingplatform.entity.comments.Comment;
import org.enigma.virtualmuseumvisitingplatform.exceptions.cutomExceptions.CommentNotFoundException;
import org.enigma.virtualmuseumvisitingplatform.exceptions.cutomExceptions.DetectSwearException;
import org.enigma.virtualmuseumvisitingplatform.mapper.CommentMapper;
import org.enigma.virtualmuseumvisitingplatform.repository.comment.CommentRepository;
import org.enigma.virtualmuseumvisitingplatform.service.abstracts.comments.ICommentService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService implements ICommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final LLamaAIAdapter llamaAIAdapter;

    @Override
    public Result saveComment(CommentSaveRequestDTO commentSaveRequestDTO) {

        // map to entity
        Comment comment = this.commentMapper.commentSaveRequestDTOToComment(commentSaveRequestDTO);

        // save comment and return commentId
        Long commentId = this.commentRepository.save(comment).getId();

        // check comment
        if(llamaAIAdapter.isSwear(comment.getText())){
            this.deleteComment(commentId);
            throw new DetectSwearException("Detected Swear");
        }
        return new SuccessResult("Comment saved");
    }

    @Override
    public Result deleteComment(Long id) {

        // find by id
        Comment comment = this.commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException("Comment not found with id: " + id));

        // set status false
        comment.setStatus(false);

        // update data
        this.commentRepository.save(comment);
        return new SuccessResult("Comment deleted");
    }

    @Override
    public Result updateComment() {
        return null;
    }

    @Override
    public DataResult<List<CommentResponseDTO>> findByArtifactId(Long artifactId) {
        List<Comment> comments = this.commentRepository.findByArtifactId(artifactId);

        List<CommentResponseDTO> commentResponseDTOS = this.commentMapper.commentToCommentResponseDTOList(comments);

        return new SuccessDataResult<>(commentResponseDTOS, "Successfully retrieved comments");
    }
}
