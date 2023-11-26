package com.restaurant.dtos.responses;

import com.restaurant.enums.UserRole;
import lombok.Data;

@Data
public class LoginResponse {
    private String jwt;
    private UserRole userRole;
    private Long userId;
}
