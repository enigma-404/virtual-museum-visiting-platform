package org.enigma.virtualmuseumvisitingplatform.exceptions;

import org.enigma.virtualmuseumvisitingplatform.core.utilities.result.DataResult;
import org.enigma.virtualmuseumvisitingplatform.security.business.CustomUserDetailsService;
import org.enigma.virtualmuseumvisitingplatform.security.jwt.JWTUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@WebMvcTest(GlobalExceptionHandler.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JWTUtils jwtUtils;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(globalExceptionHandler).build();
    }

    @Test
    void testHandlingMethodArgumentValid() throws Exception {
        // Arrange
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(
                Collections.singletonList(new FieldError("objectName", "field", "defaultMessage"))
        );

        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

        // Act
        ResponseEntity<DataResult<?>> response = globalExceptionHandler.handlingMethodArgumentValid(exception);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("validation error", response.getBody().getMessage());
        assertTrue(response.getBody().getData() instanceof Map);

        Map<String, String> errors = (Map<String, String>) response.getBody().getData();
        assertEquals(1, errors.size());
        assertEquals("defaultMessage", errors.get("field"));
    }



    @Test
    void testHandleRoleNotFountException() throws Exception {
        ResponseEntity<String> responseEntity = globalExceptionHandler.handleRoleNotFountException();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("role not found", responseEntity.getBody());
    }

    @Test
    void testHandleExistsUserException() throws Exception {
        ResponseEntity<String> responseEntity = globalExceptionHandler.handleExistsUserException();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("User not found", responseEntity.getBody());
    }

    @Test
    void testHandleMuseumNotFoundException() throws Exception {
        ResponseEntity<String> responseEntity = globalExceptionHandler.handleMuseumNotFoundException();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Museum not found", responseEntity.getBody());
    }

    @Test
    void testHandleCategoryNotFoundException() throws Exception {
        ResponseEntity<String> responseEntity = globalExceptionHandler.handleCategoryNotFoundException();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Category not found", responseEntity.getBody());
    }

    @Test
    void testHandleCommentNotFoundException() throws Exception {
        ResponseEntity<String> responseEntity = globalExceptionHandler.handleCommentNotFoundException();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Comment not found", responseEntity.getBody());
    }

    @Test
    void testDetectSwearException() throws Exception {
        ResponseEntity<String> responseEntity = globalExceptionHandler.detectSwearException();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("detect swear in comment", responseEntity.getBody());
    }

    @Test
    void testArtifactNotFoundException() throws Exception {
        ResponseEntity<String> responseEntity = globalExceptionHandler.artifactNotFoundException();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("artifact not found", responseEntity.getBody());
    }
}
