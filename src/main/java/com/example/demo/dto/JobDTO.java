package com.example.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class JobDTO {
	@NotBlank(message = "Title cannot be empty")
    private String title;

    @Size(min = 10, message = "Description should be extended ")
    private String description;

    @Min(value = 500, message = "min budget is 500")
    private Double budget;

}
