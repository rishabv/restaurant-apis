package com.restaurant.controllers;

import com.restaurant.dtos.requests.LoginRequest;
import com.restaurant.dtos.responses.LoginResponse;
import com.restaurant.dtos.requests.SignupRequest;
import com.restaurant.dtos.UserDto;
import com.restaurant.entities.User;
import com.restaurant.repositories.UserRepository;
import com.restaurant.services.auth.AuthService;
import com.restaurant.services.jwt.UserDetailsServiceImpl;
import com.restaurant.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    private final AuthService authService;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsServiceImpl userDetailsService;

    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService, JwtUtil jwtUtil, UserRepository userRepository){
        this.authService=authService;
        this.authenticationManager=authenticationManager;
        this.userDetailsService=userDetailsService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest){
        UserDto createdUser = authService.createUser(signupRequest);
        log.info("Here is the password"+signupRequest.getPassword());
        if(createdUser==null){
            return new ResponseEntity<>("User not created.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public LoginResponse loginUser(@RequestBody LoginRequest request, HttpServletResponse response) throws IOException {
        log.info("Here is the password"+request.getPassword());
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (BadCredentialsException e){
            log.info("Incorrect Username of password");
            throw new BadCredentialsException("Incorrect Username of password");
//            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not active");
        } catch (DisabledException e){
            log.info("User not active");
            return null;
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        final String token = jwtUtil.generateToken(userDetails);
        Optional<User> user = userRepository.findFirstByEmail(userDetails.getUsername());
        LoginResponse loginResponse = new LoginResponse();
        if(user.isPresent()){
            loginResponse.setUserId(user.get().getId());
            loginResponse.setJwt(token);
            loginResponse.setUserRole(user.get().getRole());
        }
        return loginResponse;
    }
}
