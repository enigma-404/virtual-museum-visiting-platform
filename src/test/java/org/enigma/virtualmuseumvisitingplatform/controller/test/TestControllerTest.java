package org.enigma.virtualmuseumvisitingplatform.controller.test;

import org.enigma.virtualmuseumvisitingplatform.security.business.CustomUserDetailsService;
import org.enigma.virtualmuseumvisitingplatform.security.jwt.JWTUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(TestController.class)
public class TestControllerTest {

    @MockBean
    private JWTUtils jwtUtils;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    public void testHelloWhenCalledThenReturnsHelloWorld() throws Exception {
        // Arrange & Act
        mockMvc.perform(MockMvcRequestBuilders.get("/api/test/hello"))
                // Assert
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Hello World"));
    }
}
