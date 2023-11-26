package com.restaurant.services.jwt;

import com.restaurant.entities.User;
import com.restaurant.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    UserDetailsServiceImpl(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> foundUser = userRepository.findFirstByEmail(email);
        log.info(foundUser.get().getEmail());
        if(foundUser.isEmpty()){
            throw new UsernameNotFoundException("User not found", null);
        }
        return new org.springframework.security.core.userdetails.User(foundUser.get().getEmail(), foundUser.get().getPassword(), new ArrayList<>());
    }
}
