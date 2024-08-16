package org.enigma.virtualmuseumvisitingplatform.controller.pictures;

import org.enigma.virtualmuseumvisitingplatform.security.business.CustomUserDetailsService;
import org.enigma.virtualmuseumvisitingplatform.security.jwt.JWTUtils;
import org.enigma.virtualmuseumvisitingplatform.service.abstracts.pictures.IPictureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PictureController.class)
class PictureControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JWTUtils jwtUtils;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;


    @MockBean
    private IPictureService pictureService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    void testGetPicture() throws Exception {
        // Arrange
        String fileName = "testImage.png";
        byte[] imageContent = "fakeImageData".getBytes(); // Simulate image byte content

        when(pictureService.getPicture(anyString())).thenReturn(imageContent);

        // Act & Assert
        mockMvc.perform(get("/api/picture/{fileName}", fileName))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_PNG))
                .andExpect(content().bytes(imageContent));
    }
}
