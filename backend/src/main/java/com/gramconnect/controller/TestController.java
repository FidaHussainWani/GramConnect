package com.gramconnect.controller;

import com.gramconnect.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/test")
    public String test(Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        return "Welcome " + user.getName()
                + "! Your JWT authentication is working.";
    }
}