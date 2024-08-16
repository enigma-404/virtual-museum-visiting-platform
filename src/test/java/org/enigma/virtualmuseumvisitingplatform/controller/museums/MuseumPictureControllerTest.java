package org.enigma.virtualmuseumvisitingplatform.controller.museums;

import org.enigma.virtualmuseumvisitingplatform.core.utilities.result.DataResult;
import org.enigma.virtualmuseumvisitingplatform.core.utilities.result.Result;
import org.enigma.virtualmuseumvisitingplatform.security.business.CustomUserDetailsService;
import org.enigma.virtualmuseumvisitingplatform.security.jwt.JWTUtils;
import org.enigma.virtualmuseumvisitingplatform.service.abstracts.museums.IMuseumPictureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MuseumPictureController.class)
class MuseumPictureControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JWTUtils jwtUtils;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private IMuseumPictureService museumPictureService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    void testSavePicture() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test image content".getBytes());

        Result result = new Result(true, "Picture saved successfully");
        when(museumPictureService.savePicture(anyLong(), eq(file))).thenReturn(result);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/museumPictures")
                        .file(file)
                        .param("museumId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Picture saved successfully"));
    }

    @Test
    void testFindByMuseumId() throws Exception {
        DataResult<List<String>> dataResult = new DataResult<>(true, Arrays.asList("pic1.jpg", "pic2.jpg"), "Pictures found");
        when(museumPictureService.findPictureByMuseumId(anyLong())).thenReturn(dataResult);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/museumPictures/findByMuseumId")
                        .param("museumId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0]").value("pic1.jpg"))
                .andExpect(jsonPath("$.data[1]").value("pic2.jpg"))
                .andExpect(jsonPath("$.message").value("Pictures found"));
    }
}
