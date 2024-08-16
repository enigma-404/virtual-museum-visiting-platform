package org.enigma.virtualmuseumvisitingplatform.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.enigma.virtualmuseumvisitingplatform.security.entities.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.Key;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JWTUtilsTest {

    private JWTUtils jwtUtils;

    @Mock
    private Authentication authentication;

    @Mock
    private UserDetailsImpl userDetails;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        jwtUtils = new JWTUtils();

        // Use reflection to set the private fields
        setPrivateField(jwtUtils, "jwtSecret", "DWGgaQkAkvKtvT6SxFduSLiASDo7PnqGq5sSsRlbDBqImEHsfVttOlH7B21wi1cT6se");
        setPrivateField(jwtUtils, "jwtExpirationMs", 3600000); // 1 hour
    }

    private void setPrivateField(Object object, String fieldName, Object value) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }

    private Key getPrivateKey() throws Exception {
        Method method = JWTUtils.class.getDeclaredMethod("key");
        method.setAccessible(true);
        return (Key) method.invoke(jwtUtils);
    }

    @Test
    void testGenerateJwtToken() throws Exception {
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");

        String token = jwtUtils.generateJwtToken(authentication);

        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    void testGetUserNameFromJwtToken() throws Exception {
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");

        String token = jwtUtils.generateJwtToken(authentication);
        String username = jwtUtils.getUserNameFromJwtToken(token);

        assertEquals("testUser", username);
    }

    @Test
    void testValidateJwtToken_ValidToken() throws Exception {
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");

        String token = jwtUtils.generateJwtToken(authentication);
        assertTrue(jwtUtils.validateJwtToken(token));
    }

    @Test
    void testValidateJwtToken_InvalidToken() {
        String invalidToken = "invalidtoken";
        assertFalse(jwtUtils.validateJwtToken(invalidToken));
    }

    @Test
    void testValidateJwtToken_ExpiredToken() throws Exception {
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");

        setPrivateField(jwtUtils, "jwtExpirationMs", 1); // Set expiration to 1ms for testing
        String token = jwtUtils.generateJwtToken(authentication);

        // Wait for the token to expire
        Thread.sleep(2);

        assertFalse(jwtUtils.validateJwtToken(token));
    }

    @Test
    void testKeyGeneration() throws Exception {
        Key key = getPrivateKey();
        assertNotNull(key);
    }

    @Test
    void testInvalidJwtToken_MalformedJwtException() {
        String malformedToken = "malformed.token";
        assertFalse(jwtUtils.validateJwtToken(malformedToken));
    }

    @Test
    void testInvalidJwtToken_EmptyJwtException() {
        String emptyToken = "";
        assertFalse(jwtUtils.validateJwtToken(emptyToken));
    }
}
