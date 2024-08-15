package org.enigma.virtualmuseumvisitingplatform.controller.museums;

import org.enigma.virtualmuseumvisitingplatform.core.result.DataResult;
import org.enigma.virtualmuseumvisitingplatform.core.result.Result;
import org.enigma.virtualmuseumvisitingplatform.dto.request.museum.MuseumSaveRequestDTO;
import org.enigma.virtualmuseumvisitingplatform.dto.request.museum.MuseumUpdateRequestDTO;
import org.enigma.virtualmuseumvisitingplatform.dto.response.museum.MuseumResponseDTO;
import org.enigma.virtualmuseumvisitingplatform.security.business.CustomUserDetailsService;
import org.enigma.virtualmuseumvisitingplatform.security.jwt.JWTUtils;
import org.enigma.virtualmuseumvisitingplatform.service.abstracts.museums.IMuseumService;
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

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MuseumController.class)
public class MuseumControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JWTUtils jwtUtils;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private IMuseumService museumService;

    private MuseumResponseDTO museumResponseDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();

        museumResponseDTO = new MuseumResponseDTO();
        museumResponseDTO.setId(1L);
        museumResponseDTO.setName("The Louvre");
        museumResponseDTO.setDescription("The world's largest art museum.");
    }

    @Test
    public void testFindById() throws Exception {
        BDDMockito.given(museumService.findMuseumById(anyLong()))
                .willReturn(new DataResult<>(true, museumResponseDTO));

        mockMvc.perform(get("/api/museums")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"success\":true,\"data\":{\"id\":1,\"name\":\"The Louvre\",\"description\":\"The world's largest art museum.\"}}"));
    }

    @Test
    public void testFindAll() throws Exception {
        List<MuseumResponseDTO> museumList = Collections.singletonList(museumResponseDTO);
        BDDMockito.given(museumService.findAllByPageNumberAndPageSize(anyInt(), anyInt()))
                .willReturn(new DataResult<>(true, museumList));

        mockMvc.perform(get("/api/museums/findAll")
                        .param("pageNumber", "0")
                        .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"success\":true,\"data\":[{\"id\":1,\"name\":\"The Louvre\",\"description\":\"The world's largest art museum.\"}]}"));
    }

    @Test
    public void testSave() throws Exception {
        MuseumSaveRequestDTO saveRequest = new MuseumSaveRequestDTO();
        saveRequest.setName("The Louvre");
        saveRequest.setDescription("The world's largest art museum.");

        BDDMockito.given(museumService.saveMuseum(any(MuseumSaveRequestDTO.class)))
                .willReturn(new Result(true, "Museum saved successfully"));

        mockMvc.perform(post("/api/museums")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"The Louvre\",\"location\":\"Paris, France\",\"description\":\"The world's largest art museum.\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"success\":true,\"message\":\"Museum saved successfully\"}"));
    }

    @Test
    public void testUpdate() throws Exception {
        MuseumUpdateRequestDTO updateRequest = new MuseumUpdateRequestDTO();
        updateRequest.setName("The Updated Louvre");
        updateRequest.setDescription("Updated description.");

        BDDMockito.given(museumService.updateMuseum(anyLong(), any(MuseumUpdateRequestDTO.class)))
                .willReturn(new Result(true, "Museum updated successfully"));

        mockMvc.perform(put("/api/museums")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"The Updated Louvre\",\"location\":\"Updated Location\",\"description\":\"Updated description.\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"success\":true,\"message\":\"Museum updated successfully\"}"));
    }
}
