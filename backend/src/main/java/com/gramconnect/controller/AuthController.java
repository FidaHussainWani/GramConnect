package com.gramconnect.controller;

import com.gramconnect.dto.RegisterRequest;
import com.gramconnect.dto.UserResponse;
import com.gramconnect.entity.User;
import com.gramconnect.security.JwtService;
import com.gramconnect.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.gramconnect.security.JwtService;
import com.gramconnect.dto.LoginRequest;
import com.gramconnect.dto.LoginResponse;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        User user = userService.register(request);

        UserResponse response = UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
    @PostMapping("/login")
public ResponseEntity<LoginResponse> login(
        @Valid @RequestBody LoginRequest request) {

    User user = userService.login(
            request.getEmail(),
            request.getPassword()
    );

    String token = jwtService.generateToken(user.getEmail());

    LoginResponse response = LoginResponse.builder()
            .token(token)
            .userId(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .role(user.getRole())
            .build();

    return ResponseEntity.ok(response);
}
}