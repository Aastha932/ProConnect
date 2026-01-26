package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDto {
	private Long id;
	@NotBlank(message = "name is required")
    private String name;
	
	@Email(message = "please provide a  valid email address")
	@NotBlank(message = "email is required")
    private String email;

}
