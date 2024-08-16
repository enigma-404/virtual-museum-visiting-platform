package org.enigma.virtualmuseumvisitingplatform.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.enigma.virtualmuseumvisitingplatform.security.business.CustomUserDetailsService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthTokenFilterTest {

    @Mock
    private JWTUtils jwtUtils;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private AuthTokenFilter authTokenFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext(); // Clear context before each test
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext(); // Clear context after each test
    }

    @Test
    void testDoFilterInternal_WhenJwtIsValid() throws ServletException, IOException {
        when(request.getHeader(eq("Authorization"))).thenReturn("Bearer valid-jwt-token");
        when(jwtUtils.validateJwtToken(eq("valid-jwt-token"))).thenReturn(true);
        when(jwtUtils.getUserNameFromJwtToken(eq("valid-jwt-token"))).thenReturn("testUser");
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userDetails.getAuthorities()).thenReturn(Collections.emptyList());

        when(customUserDetailsService.loadUserByUsername(eq("testUser"))).thenReturn(userDetails);

        authTokenFilter.doFilterInternal(request, response, filterChain);

        verify(customUserDetailsService, times(1)).loadUserByUsername(eq("testUser"));
        verify(filterChain, times(1)).doFilter(request, response);

        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authentication);
        assertEquals(userDetails, authentication.getPrincipal());
    }

    @Test
    void testDoFilterInternal_WhenJwtIsInvalid() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("Bearer invalid-jwt-token");
        when(jwtUtils.validateJwtToken("invalid-jwt-token")).thenReturn(false);

        authTokenFilter.doFilterInternal(request, response, filterChain);

        verify(customUserDetailsService, never()).loadUserByUsername(anyString());
        verify(filterChain, times(1)).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testDoFilterInternal_WhenNoJwtProvided() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        authTokenFilter.doFilterInternal(request, response, filterChain);

        verify(customUserDetailsService, never()).loadUserByUsername(anyString());
        verify(filterChain, times(1)).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testDoFilterInternal_WhenExceptionThrown() throws ServletException, IOException {
        when(request.getHeader(eq("Authorization"))).thenReturn("Bearer invalid-jwt-token");
        when(jwtUtils.validateJwtToken(anyString())).thenThrow(new RuntimeException("Test Exception"));

        authTokenFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication(),
                "Expected authentication to be null after exception.");
    }
}
