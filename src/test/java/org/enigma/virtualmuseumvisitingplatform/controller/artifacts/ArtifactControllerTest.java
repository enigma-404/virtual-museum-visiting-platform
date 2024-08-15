package org.enigma.virtualmuseumvisitingplatform.controller.artifacts;

import org.enigma.virtualmuseumvisitingplatform.core.result.DataResult;
import org.enigma.virtualmuseumvisitingplatform.core.result.Result;
import org.enigma.virtualmuseumvisitingplatform.dto.request.artifact.ArtifactUpdateRequestDTO;
import org.enigma.virtualmuseumvisitingplatform.dto.response.artifact.ArtifactResponseDTO;
import org.enigma.virtualmuseumvisitingplatform.security.business.CustomUserDetailsService;
import org.enigma.virtualmuseumvisitingplatform.security.jwt.JWTUtils;
import org.enigma.virtualmuseumvisitingplatform.service.abstracts.artifacts.IArtifactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ArtifactController.class)
class ArtifactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JWTUtils jwtUtils;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private IArtifactService artifactService;

    private ArtifactResponseDTO artifactResponseDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
        artifactResponseDTO = new ArtifactResponseDTO();
        artifactResponseDTO.setId(1L);
        artifactResponseDTO.setName("Ancient Vase");
        artifactResponseDTO.setDescription("A beautiful ancient vase from the Ming Dynasty.");
        artifactResponseDTO.setLocation("Exhibit Hall A");
        artifactResponseDTO.setLikeCount(150);
        artifactResponseDTO.setDislikeCount(5);
    }

    @Test
    void testFindById() throws Exception {
        Mockito.when(artifactService.findById(anyLong()))
                .thenReturn(new DataResult<>(true, artifactResponseDTO));

        mockMvc.perform(get("/api/artifacts")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(artifactResponseDTO.getId()))
                .andExpect(jsonPath("$.data.name").value(artifactResponseDTO.getName()))
                .andExpect(jsonPath("$.data.description").value(artifactResponseDTO.getDescription()))
                .andExpect(jsonPath("$.data.location").value(artifactResponseDTO.getLocation()))
                .andExpect(jsonPath("$.data.likeCount").value(artifactResponseDTO.getLikeCount()))
                .andExpect(jsonPath("$.data.dislikeCount").value(artifactResponseDTO.getDislikeCount()));

    }

    @Test
    void testFindAll() throws Exception {
        List<ArtifactResponseDTO> artifacts = List.of(artifactResponseDTO);
        Mockito.when(artifactService.findAllByPageNumberAndPageSize(anyInt(), anyInt()))
                .thenReturn(new DataResult<>(true, artifacts));

        mockMvc.perform(get("/api/artifacts/findAll")
                        .param("pageNumber", "1")
                        .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.length()").value(artifacts.size()));
    }


    @Test
    void testUpdateArtifact() throws Exception {
        ArtifactUpdateRequestDTO updateRequestDTO = new ArtifactUpdateRequestDTO();
        updateRequestDTO.setName("Updated Artifact");
        updateRequestDTO.setDescription("An updated description.");
        updateRequestDTO.setLocation("Updated Location");

        Mockito.when(artifactService.updateArtifact(anyLong(), any(ArtifactUpdateRequestDTO.class)))
                .thenReturn(new Result(true));

        mockMvc.perform(put("/api/artifacts")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Updated Artifact\", \"description\": \"An updated description.\", \"location\": \"Updated Location\" }"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true));
    }


}
