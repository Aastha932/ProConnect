package com.example.demo.advices;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
@AllArgsConstructor
public class ApiError {
	private HttpStatus status;
    private String message;
    private List<String> errors; // Multiple validation errors ke liye
}
