package com.example.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.entities.UserEntity;
import com.example.demo.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service

public class AuthService {
	private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }
//sign up logic
    public String signup(UserEntity user) {
       
        user.setPassword(passwordEncoder.encode(user.getPassword())); 
        if (user.getRole() == null) {
            user.setRole(com.example.demo.entities.enums.Role.CANDIDATE);
        }
        userRepository.save(user);
        return "User registered successfully!";
    }  
    //login logic
    public AuthResponse login(LoginRequest request) {
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

      
        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
           
        	String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            return new AuthResponse(accessToken, refreshToken);
        } else {
            throw new RuntimeException("Invalid Credentials");
        }
    }
    public AuthResponse refresh(String refreshToken) {
    	if (refreshToken == null || refreshToken.isBlank()) {
            throw new RuntimeException("Refresh token is missing in Service!");
        }
      
        String email = jwtService.getEmailFromToken(refreshToken);
      
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    
        String newAccessToken = jwtService.generateAccessToken(user);

        return new AuthResponse(newAccessToken, refreshToken);
    }
    
    
}
