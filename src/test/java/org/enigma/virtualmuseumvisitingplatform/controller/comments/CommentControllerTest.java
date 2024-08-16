package org.enigma.virtualmuseumvisitingplatform.controller.comments;

import org.enigma.virtualmuseumvisitingplatform.core.utilities.result.Result;
import org.enigma.virtualmuseumvisitingplatform.dto.request.comment.CommentSaveRequestDTO;
import org.enigma.virtualmuseumvisitingplatform.security.business.CustomUserDetailsService;
import org.enigma.virtualmuseumvisitingplatform.security.jwt.JWTUtils;
import org.enigma.virtualmuseumvisitingplatform.service.abstracts.comments.ICommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JWTUtils jwtUtils;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private ICommentService commentService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    public void testSaveCommentWhenCommentIsSavedThenReturnOk() throws Exception {
        // Arrange
        CommentSaveRequestDTO requestDTO = new CommentSaveRequestDTO();
        requestDTO.setText("This is a comment");
        requestDTO.setUserId(1L);
        requestDTO.setArtifactId(1L);

        Result result = new Result(true, "Comment saved successfully");
        BDDMockito.given(commentService.saveComment(any(CommentSaveRequestDTO.class))).willReturn(result);

        // Act & Assert
        mockMvc.perform(post("/api/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"text\":\"This is a comment\",\"userId\":1,\"artifactId\":1}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"success\":true,\"message\":\"Comment saved successfully\"}"));
    }

    @Test
    public void testSaveCommentWhenRequestBodyIsNullThenReturnBadRequest() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/api/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSaveCommentWhenRequestBodyIsInvalidThenReturnBadRequest() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/api/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"text\":\"\",\"userId\":null,\"artifactId\":null}"))
                .andExpect(status().isBadRequest());
    }
}
