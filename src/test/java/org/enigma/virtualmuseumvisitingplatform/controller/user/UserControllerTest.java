package org.enigma.virtualmuseumvisitingplatform.controller.user;

import org.enigma.virtualmuseumvisitingplatform.core.utilities.result.DataResult;
import org.enigma.virtualmuseumvisitingplatform.core.utilities.result.Result;
import org.enigma.virtualmuseumvisitingplatform.entity.artifacts.Artifact;
import org.enigma.virtualmuseumvisitingplatform.entity.users.User;
import org.enigma.virtualmuseumvisitingplatform.repository.user.UserRepository;
import org.enigma.virtualmuseumvisitingplatform.security.business.CustomUserDetailsService;
import org.enigma.virtualmuseumvisitingplatform.security.jwt.JWTUtils;
import org.enigma.virtualmuseumvisitingplatform.service.abstracts.users.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@WebMvcTest(UserControllerTest.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JWTUtils jwtUtils;

    @Mock
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private IUserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    void testAddFavourite() {
        // Arrange
        long userId = 1L;
        long artifactId = 1L;
        Result result = mock(Result.class);
        when(userService.addFavourite(userId, artifactId)).thenReturn(result);

        // Act
        ResponseEntity<Result> response = userController.addFavourite(userId, artifactId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(result, response.getBody());
        verify(userService, times(1)).addFavourite(userId, artifactId);
    }

    @Test
    void testGetFavourite() {
        // Arrange
        long userId = 1L;
        List<Artifact> artifacts = new ArrayList<>();
        DataResult<List<Artifact>> dataResult = mock(DataResult.class);
        when(dataResult.getData()).thenReturn(artifacts);
        when(userService.getFavourites(userId)).thenReturn(dataResult);

        // Act
        ResponseEntity<DataResult<List<Artifact>>> response = userController.getFavourite(userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dataResult, response.getBody());
        verify(userService, times(1)).getFavourites(userId);
    }


    @Test
    void testGetFavourites_Success() {
        // Arrange
        long userId = 1L;
        List<Artifact> artifacts = new ArrayList<>();
        User user = new User();
        user.setId(userId);
        user.setSavedArtifacts(artifacts);

        DataResult<List<Artifact>> expectedResult = new DataResult<>(true, artifacts, "successfully found artifacts");

        // Mock the behavior of the userRepository
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Mock the behavior of the userService
        when(userService.getFavourites(userId)).thenReturn(expectedResult);

        // Act
        DataResult<List<Artifact>> result = userService.getFavourites(userId);

        // Assert
        assertNotNull(result);  // This will now pass
        assertTrue(result.isSuccess());
        assertEquals(artifacts, result.getData());
        assertEquals("successfully found artifacts", result.getMessage());
    }

}