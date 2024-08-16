package org.enigma.virtualmuseumvisitingplatform.service.concretes.comments;

import org.enigma.virtualmuseumvisitingplatform.core.components.LLamaAIAdapter;
import org.enigma.virtualmuseumvisitingplatform.core.utilities.result.DataResult;
import org.enigma.virtualmuseumvisitingplatform.core.utilities.result.Result;
import org.enigma.virtualmuseumvisitingplatform.dto.request.comment.CommentSaveRequestDTO;
import org.enigma.virtualmuseumvisitingplatform.dto.response.comment.CommentResponseDTO;
import org.enigma.virtualmuseumvisitingplatform.entity.comments.Comment;
import org.enigma.virtualmuseumvisitingplatform.exceptions.cutomExceptions.CommentNotFoundException;
import org.enigma.virtualmuseumvisitingplatform.exceptions.cutomExceptions.DetectSwearException;
import org.enigma.virtualmuseumvisitingplatform.mapper.CommentMapper;
import org.enigma.virtualmuseumvisitingplatform.repository.comment.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CommentMapper commentMapper;

    @Mock
    private LLamaAIAdapter llamaAIAdapter;

    @InjectMocks
    private CommentService commentService;

    private CommentSaveRequestDTO commentSaveRequestDTO;
    private Comment comment;
    private CommentResponseDTO commentResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        commentSaveRequestDTO = new CommentSaveRequestDTO();
        commentSaveRequestDTO.setText("Nice artifact");
        commentSaveRequestDTO.setUserId(1L);
        commentSaveRequestDTO.setArtifactId(1L);

        comment = new Comment();
        comment.setId(1L);
        comment.setText("Nice artifact");

        commentResponseDTO = new CommentResponseDTO();
        commentResponseDTO.setId(1L);
        commentResponseDTO.setText("Nice artifact");
    }

    @Test
    void testSaveComment_whenNoSwearWords_thenCommentSaved() {
        // Arrange
        when(commentMapper.commentSaveRequestDTOToComment(any(CommentSaveRequestDTO.class))).thenReturn(comment);
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        when(llamaAIAdapter.isSwear(any(String.class))).thenReturn(false);

        // Act
        Result result = commentService.saveComment(commentSaveRequestDTO);

        // Assert
        assertTrue(result.isSuccess());
        assertEquals("Comment saved", result.getMessage());
    }

    @Test
    void testSaveComment_whenSwearWordsDetected_thenThrowDetectSwearException() {
        // Arrange
        when(commentMapper.commentSaveRequestDTOToComment(any(CommentSaveRequestDTO.class))).thenReturn(comment);
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        when(llamaAIAdapter.isSwear(any(String.class))).thenReturn(true);
        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(comment));

        // Act & Assert
        DetectSwearException exception = assertThrows(DetectSwearException.class, () -> {
            commentService.saveComment(commentSaveRequestDTO);
        });

        assertEquals("Detected Swear", exception.getMessage());
    }

    @Test
    void testDeleteComment_whenCommentExists_thenCommentDeleted() {
        // Arrange
        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(comment));

        // Act
        Result result = commentService.deleteComment(1L);

        // Assert
        assertTrue(result.isSuccess());
        assertEquals("Comment deleted", result.getMessage());
    }

    @Test
    void testDeleteComment_whenCommentNotFound_thenThrowCommentNotFoundException() {
        // Arrange
        when(commentRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        CommentNotFoundException exception = assertThrows(CommentNotFoundException.class, () -> {
            commentService.deleteComment(1L);
        });

        assertEquals("Comment not found with id: 1", exception.getMessage());
    }

    @Test
    void testFindByArtifactId_whenCommentsExist_thenReturnCommentResponseDTOs() {
        // Arrange
        when(commentRepository.findByArtifactId(anyLong())).thenReturn(Arrays.asList(comment));
        when(commentMapper.commentToCommentResponseDTOList(any(List.class))).thenReturn(Arrays.asList(commentResponseDTO));

        // Act
        DataResult<List<CommentResponseDTO>> result = commentService.findByArtifactId(1L);

        // Assert
        assertTrue(result.isSuccess());
        assertEquals(1, result.getData().size());
        assertEquals("Successfully retrieved comments", result.getMessage());
    }
}
