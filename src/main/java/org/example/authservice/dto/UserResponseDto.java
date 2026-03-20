package org.example.authservice.dto;

import lombok.*;
import org.example.authservice.models.UserAccount;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private String name;

    private String email;

    private String password;

    private String phoneNumber;

    private Date createdAt;

    public static UserResponseDto from(UserAccount A){
        return UserResponseDto.builder()
                        .name(A.getName())
                        .email(A.getEmail())
                        .password(A.getPassword())
                        .phoneNumber(A.getPhoneNumber())
                        .build();
    }

}
