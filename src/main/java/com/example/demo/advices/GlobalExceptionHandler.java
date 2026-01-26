package com.example.demo.advices;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.exceptions.ResourceNotFoundException;

import lombok.extern.slf4j.Slf4j;
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	@ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFound(ResourceNotFoundException ex) {
		log.error("resource not found{}",ex.getMessage());
        ApiError error = ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(ex.getMessage())
                .build();
        return buildErrorResponseEntity(error);
    }
	private ResponseEntity<ApiResponse<?>> buildErrorResponseEntity(ApiError apierror){
		return new ResponseEntity<>(new ApiResponse<>(apierror),apierror.getStatus());
	}

    // 2. Validation Errors Handle  (@Valid wala)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationErrors(MethodArgumentNotValidException ex) {
    	log.warn("validation failed for request {}",ex.getBindingResult().getObjectName());
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        ApiError error = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Validation Failed")
                .errors(errors)
                .build();
        return    buildErrorResponseEntity(error);
}
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleInternalServerError(Exception ex) {
        log.error("Internal Server Error occurred: ", ex); // Pura error track log hoga
        ApiError error = ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("An unexpected error occurred: " + ex.getMessage())
                .build();
        return buildErrorResponseEntity(error);
    }
}
