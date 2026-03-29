package org.example.authservice.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.authservice.dto.AuthRequestDto;
import org.example.authservice.dto.AuthResponseDto;
import org.example.authservice.dto.UserResponseDto;
import org.example.authservice.dto.UserSignupRequestDto;
import org.example.authservice.services.AuthService;
import org.example.authservice.services.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Value("${cookie.expiry}")
    private int cookieExpiry;
    private JwtService jwtService;
    private final AuthService authService;

    private final AuthenticationManager authenticationManager;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/signup/user")
    public ResponseEntity<UserResponseDto> signUp(@RequestBody UserSignupRequestDto userSignUpRequestDto) {
        UserResponseDto userResponseDto = authService.signupUser(userSignUpRequestDto);
        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("/signing/user")
    public ResponseEntity<?> signIn(@RequestBody AuthRequestDto authRequestDto, HttpServletResponse response) {
        try {

//            System.out.println("EMAIL RECEIVED: [" + authRequestDto.getEmail() + "]");
//            System.out.println("PASSWORD RECEIVED: [" + authRequestDto.getPassword() + "]");

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequestDto.getEmail(),
                            authRequestDto.getPassword()
                    )
            );

            Map<String, Object> payload = new HashMap<>();
            payload.put("email", authRequestDto.getEmail());
            String jwtToken = jwtService.createToken(authRequestDto.getEmail());

            ResponseCookie cookie = ResponseCookie.from("JwtToken", jwtToken)
                    .httpOnly(false)
                    .secure(false)
                    .path("/")
                    .maxAge(cookieExpiry)
                    .build();

            response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            return ResponseEntity.ok(AuthResponseDto.builder().success(true).build());

        } catch (Exception e) {
            e.printStackTrace();  //
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Authentication failed");
        }

    }
    @GetMapping("/validate/user")
        public ResponseEntity<?> validate(HttpServletRequest request) {
            return new ResponseEntity<>("success", HttpStatus.OK);
        }
    }


