package com.restaurant.dtos.requests;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequest {
    private String email;
    private String password;
}
