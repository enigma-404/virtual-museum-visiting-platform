package org.enigma.virtualmuseumvisitingplatform.security.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SecurityUtilsTest {

    private SecurityContext securityContext;
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        // Mock SecurityContext and Authentication
        securityContext = mock(SecurityContext.class);
        authentication = mock(Authentication.class);
    }

    @Test
    void testGetUserName_WhenAuthenticated() {
        // Arrange
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("testUser");
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        // Mock SecurityContextHolder to return the mocked SecurityContext
        try (MockedStatic<SecurityContextHolder> mockedContextHolder = Mockito.mockStatic(SecurityContextHolder.class)) {
            mockedContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            // Act
            String username = SecurityUtils.getUserName();

            // Assert
            assertEquals("testUser", username);
        }
    }

    @Test
    void testGetUserName_WhenNoAuthentication() {
        // Arrange
        when(securityContext.getAuthentication()).thenReturn(null);

        // Mock SecurityContextHolder to return the mocked SecurityContext
        try (MockedStatic<SecurityContextHolder> mockedContextHolder = Mockito.mockStatic(SecurityContextHolder.class)) {
            mockedContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            // Act
            String username = SecurityUtils.getUserName();

            // Assert
            assertNull(username);
        }
    }

    @Test
    void testGetUserName_WhenAuthenticationHasNoUserDetails() {
        // Arrange
        // Here, we deliberately pass a non-UserDetails principal to see how the method handles it
        when(authentication.getPrincipal()).thenReturn("someOtherPrincipal");
        when(securityContext.getAuthentication()).thenReturn(authentication);

        // Mock SecurityContextHolder to return the mocked SecurityContext
        try (MockedStatic<SecurityContextHolder> mockedContextHolder = Mockito.mockStatic(SecurityContextHolder.class)) {
            mockedContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            // Act and Assert
            assertThrows(ClassCastException.class, () -> {
                SecurityUtils.getUserName();
            });
        }
    }
}
