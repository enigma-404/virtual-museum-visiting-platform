package org.enigma.virtualmuseumvisitingplatform.service.concretes.users;

import org.enigma.virtualmuseumvisitingplatform.core.result.DataResult;
import org.enigma.virtualmuseumvisitingplatform.core.result.Result;
import org.enigma.virtualmuseumvisitingplatform.dto.request.user.UserLoginDto;
import org.enigma.virtualmuseumvisitingplatform.dto.response.user.UserInfoResponse;
import org.enigma.virtualmuseumvisitingplatform.dto.response.user.UserSignupDto;
import org.enigma.virtualmuseumvisitingplatform.entity.User;
import org.enigma.virtualmuseumvisitingplatform.entity.role.ERole;
import org.enigma.virtualmuseumvisitingplatform.entity.role.Role;
import org.enigma.virtualmuseumvisitingplatform.exceptions.cutomExceptions.ExistsUserException;
import org.enigma.virtualmuseumvisitingplatform.exceptions.cutomExceptions.RoleNotFountException;
import org.enigma.virtualmuseumvisitingplatform.repository.role.RoleRepository;
import org.enigma.virtualmuseumvisitingplatform.repository.user.UserRepository;
import org.enigma.virtualmuseumvisitingplatform.security.entities.UserDetailsImpl;
import org.enigma.virtualmuseumvisitingplatform.security.jwt.JWTUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserService userService;
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JWTUtils jwtUtils;

    @BeforeEach
    void setUp() {
        authenticationManager = mock(AuthenticationManager.class);
        userRepository = mock(UserRepository.class);
        roleRepository = mock(RoleRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        jwtUtils = mock(JWTUtils.class);
        userService = new UserService(authenticationManager, userRepository, roleRepository, passwordEncoder, jwtUtils);
    }

    @Test
    void testLogin_Success() {
        // Arrange
        UserLoginDto loginDto = new UserLoginDto();
        loginDto.setUsername("testuser");
        loginDto.setPassword("password");

        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        UserDetailsImpl userDetails = new UserDetailsImpl(user.getId(), user.getUsername(), "password", Collections.emptyList());

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(jwtUtils.generateJwtToken(authentication)).thenReturn("test-jwt-token");

        // Act
        DataResult<UserInfoResponse> result = userService.login(loginDto);

        // Assert
        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals("testuser", result.getData().getUsername());
        assertEquals("test-jwt-token", result.getData().getJwtToken());

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void testRegister_Success() {
        // Arrange
        UserSignupDto signupDto = new UserSignupDto();
        signupDto.setUsername("newuser");
        signupDto.setPassword("password");

        when(userRepository.existsByUsername(signupDto.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(signupDto.getPassword())).thenReturn("encoded-password");

        Role userRole = new Role();
        userRole.setName(ERole.USER);

        when(roleRepository.findByName(ERole.USER)).thenReturn(Optional.of(userRole));

        // Act
        Result result = userService.register(signupDto);

        // Assert
        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals("User successfully added", result.getMessage());

        verify(userRepository, times(1)).existsByUsername(signupDto.getUsername());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegister_UserAlreadyExists() {
        // Arrange
        UserSignupDto signupDto = new UserSignupDto();
        signupDto.setUsername("existinguser");

        when(userRepository.existsByUsername(signupDto.getUsername())).thenReturn(true);

        // Act & Assert
        assertThrows(ExistsUserException.class, () -> userService.register(signupDto));

        verify(userRepository, times(1)).existsByUsername(signupDto.getUsername());
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void testRegister_RoleNotFound() {
        // Arrange
        UserSignupDto signupDto = new UserSignupDto();
        signupDto.setUsername("newuser");

        when(userRepository.existsByUsername(signupDto.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(signupDto.getPassword())).thenReturn("encoded-password");

        when(roleRepository.findByName(ERole.USER)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RoleNotFountException.class, () -> userService.register(signupDto));

        verify(roleRepository, times(1)).findByName(ERole.USER);
        verify(userRepository, times(0)).save(any(User.class));
    }
}
