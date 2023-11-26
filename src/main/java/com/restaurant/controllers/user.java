package com.restaurant.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class user {
    @GetMapping("/me")
    public String getMe(){
        return "Hello World!!";
    }
}
