package org.enigma.virtualmuseumvisitingplatform.controller.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.enigma.virtualmuseumvisitingplatform.core.result.DataResult;
import org.enigma.virtualmuseumvisitingplatform.core.result.Result;
import org.enigma.virtualmuseumvisitingplatform.dto.request.user.UserLoginDto;
import org.enigma.virtualmuseumvisitingplatform.dto.response.user.UserInfoResponse;
import org.enigma.virtualmuseumvisitingplatform.dto.response.user.UserSignupDto;
import org.enigma.virtualmuseumvisitingplatform.service.abstracts.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IUserService userService;

    @PostMapping("/login")
    public ResponseEntity<DataResult<UserInfoResponse>> login(@RequestBody UserLoginDto userLoginDto) {
        return ResponseEntity.ok(this.userService.login(userLoginDto));
    }

    @PostMapping("/register")
    public ResponseEntity<Result> register(@Valid @RequestBody UserSignupDto userSignupDto){
        return ResponseEntity.ok(this.userService.register(userSignupDto));
    }
}
