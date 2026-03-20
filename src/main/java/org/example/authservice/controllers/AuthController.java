package org.example.authservice.controllers;

import org.example.authservice.dto.UserResponseDto;
import org.example.authservice.dto.UserSignupRequestDto;
import org.example.authservice.services.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }
    @PostMapping("/signup/user")
    public ResponseEntity<UserResponseDto> signUp(@RequestBody UserSignupRequestDto userSignUpRequestDto){

        UserResponseDto userResponseDto = authService.signupUser(userSignUpRequestDto);
        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }


}
