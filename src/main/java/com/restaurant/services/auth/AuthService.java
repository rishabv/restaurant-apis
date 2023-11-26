package com.restaurant.services.auth;

import com.restaurant.dtos.requests.SignupRequest;
import com.restaurant.dtos.UserDto;

public interface AuthService {
    UserDto createUser(SignupRequest signupRequest);
}
