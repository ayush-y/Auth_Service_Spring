package org.example.authservice.services;

import org.example.authservice.dto.UserResponseDto;
import org.example.authservice.dto.UserSignupRequestDto;
import org.example.authservice.models.UserAccount;
import org.example.authservice.repositories.UserAccountRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserAccountRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    public AuthService(UserAccountRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDto signupUser(UserSignupRequestDto userSignUpRequestDto){

        UserAccount users = UserAccount.builder()
                .email(userSignUpRequestDto.getEmail())
                .name(userSignUpRequestDto.getName())
                .password(passwordEncoder.encode(userSignUpRequestDto.getPassword()))  //Todo encrypt the password
                .phoneNumber(userSignUpRequestDto.getPhoneNumber())
                .build();

        UserAccount user = userRepository.save(users);


        return UserResponseDto.from(user);

    }
}
