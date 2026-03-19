package org.example.authservice.Controller;

import org.example.authservice.dto.UserDtoResponse;
import org.example.authservice.dto.UserSignUpRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @PostMapping("/signup")
    public ResponseEntity<UserDtoResponse> signUp(@RequestBody UserSignUpRequestDto userSignUpRequestDto){

        return null;
    }


}
