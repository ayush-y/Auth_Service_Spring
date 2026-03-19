package org.example.authservice.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoResponse {

    private String name;

    private String email;

    private String password;

    private String phoneNumber;

    private Date createdAt;

}
