package org.enigma.virtualmuseumvisitingplatform.controller.user;

import org.enigma.virtualmuseumvisitingplatform.core.result.DataResult;
import org.enigma.virtualmuseumvisitingplatform.core.result.Result;
import org.enigma.virtualmuseumvisitingplatform.dto.request.user.UserLoginDto;
import org.enigma.virtualmuseumvisitingplatform.dto.response.user.UserInfoResponse;
import org.enigma.virtualmuseumvisitingplatform.dto.response.user.UserSignupDto;
import org.enigma.virtualmuseumvisitingplatform.security.business.CustomUserDetailsService;
import org.enigma.virtualmuseumvisitingplatform.security.jwt.JWTUtils;
import org.enigma.virtualmuseumvisitingplatform.service.abstracts.users.IUserService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JWTUtils jwtUtils;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private IUserService userService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    public void testLogin() throws Exception {
        UserLoginDto loginRequest = new UserLoginDto();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("password");

        UserInfoResponse userInfoResponse = new UserInfoResponse();
        userInfoResponse.setId(1L);
        userInfoResponse.setUsername("testuser");
        userInfoResponse.setJwtToken("test-jwt-token");
        userInfoResponse.setRoles(Collections.singletonList("ROLE_USER"));

        DataResult<UserInfoResponse> dataResult = new DataResult<>(true, userInfoResponse);
        BDDMockito.given(userService.login(any(UserLoginDto.class))).willReturn(dataResult);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"testuser\",\"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"success\":true,\"data\":{\"id\":1,\"username\":\"testuser\",\"jwtToken\":\"test-jwt-token\",\"roles\":[\"ROLE_USER\"]}}"));
    }

    @Test
    public void testRegister() throws Exception {
        UserSignupDto signupRequest = new UserSignupDto();
        signupRequest.setUsername("newuser");
        signupRequest.setPassword("password");

        BDDMockito.given(userService.register(any(UserSignupDto.class)))
                .willReturn(new Result(true, "User registered successfully"));

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"newuser\",\"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"success\":true,\"message\":\"User registered successfully\"}"));
    }

    @Test
    public void testRegisterWhenRequestBodyIsInvalidThenReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"\",\"password\":\"\"}"))
                .andExpect(status().isBadRequest());
    }
}
