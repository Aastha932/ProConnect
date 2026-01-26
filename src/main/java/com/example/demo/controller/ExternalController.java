package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ExternalApiService;

@RestController
@RequestMapping("/api/v1/external")
public class ExternalController {
	private final ExternalApiService externalService;

    public ExternalController(ExternalApiService externalService) {
        this.externalService = externalService;
    }

    @GetMapping("/posts")
    public List<Object> fetchPosts() {
        return externalService.getexternalData();
    }
}
