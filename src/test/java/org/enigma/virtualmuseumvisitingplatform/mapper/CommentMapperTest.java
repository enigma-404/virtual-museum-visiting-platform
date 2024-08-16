package org.enigma.virtualmuseumvisitingplatform.mapper;

import org.enigma.virtualmuseumvisitingplatform.dto.request.comment.CommentSaveRequestDTO;
import org.enigma.virtualmuseumvisitingplatform.dto.response.comment.CommentResponseDTO;
import org.enigma.virtualmuseumvisitingplatform.entity.artifacts.Artifact;
import org.enigma.virtualmuseumvisitingplatform.entity.comments.Comment;
import org.enigma.virtualmuseumvisitingplatform.entity.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommentMapperTest {

    private CommentMapper commentMapper;

    @BeforeEach
    void setUp() {
        commentMapper = Mappers.getMapper(CommentMapper.class);
    }

    @Test
    void testCommentSaveRequestDTOToComment() {
        CommentSaveRequestDTO dto = new CommentSaveRequestDTO();
        dto.setText("This is a test comment");
        dto.setUserId(1L);
        dto.setArtifactId(2L);

        Comment comment = commentMapper.commentSaveRequestDTOToComment(dto);

        assertNotNull(comment);
        assertEquals("This is a test comment", comment.getText());
        assertEquals(1L, comment.getUser().getId());
        assertEquals(2L, comment.getArtifact().getId());
    }

    @Test
    void testCommentToCommentResponseDTOList() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        Artifact artifact = new Artifact();
        artifact.setId(2L);
        artifact.setName("Test Artifact");

        Comment comment1 = new Comment();
        comment1.setId(1L);
        comment1.setText("First Comment");
        comment1.setUser(user);
        comment1.setArtifact(artifact);

        Comment comment2 = new Comment();
        comment2.setId(2L);
        comment2.setText("Second Comment");
        comment2.setUser(user);
        comment2.setArtifact(artifact);

        List<Comment> comments = Arrays.asList(comment1, comment2);

        List<CommentResponseDTO> dtos = commentMapper.commentToCommentResponseDTOList(comments);

        assertNotNull(dtos);
        assertEquals(2, dtos.size());

        assertEquals(1L, dtos.get(0).getId());
        assertEquals("First Comment", dtos.get(0).getText());

        assertEquals(2L, dtos.get(1).getId());
        assertEquals("Second Comment", dtos.get(1).getText());
    }
}
