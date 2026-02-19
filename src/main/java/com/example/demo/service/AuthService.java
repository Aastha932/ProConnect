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
        userRepository.save(user);
        return "User registered successfully!";
    }  
    //login logic
    public AuthResponse login(LoginRequest request) {
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

      
        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
           
            String token = jwtService.generateToken(user.getEmail());
            return new AuthResponse(token);
        } else {
            
            throw new RuntimeException("Invalid Credentials");
        }
    }
}
