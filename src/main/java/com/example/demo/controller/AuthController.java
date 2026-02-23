package com.example.demo.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RefreshRequest;
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
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@org.springframework.web.bind.annotation.RequestBody RefreshRequest request) {
        String refreshToken = request.getRefreshToken();
        AuthResponse response = authService.refresh(refreshToken);
        return ResponseEntity.ok(response);
    }
}
