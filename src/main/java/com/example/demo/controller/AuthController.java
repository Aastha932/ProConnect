package com.example.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.entities.UserEntity;
import com.example.demo.service.AuthService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public String signup(@org.springframework.web.bind.annotation.RequestBody  UserEntity user) {
        return authService.signup(user);
    }

    @PostMapping("/login")
    public AuthResponse login(@org.springframework.web.bind.annotation.RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
