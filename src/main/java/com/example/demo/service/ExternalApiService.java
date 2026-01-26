package com.example.demo.service;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ExternalApiService {
	private final RestClient restClient;
	public ExternalApiService(RestClient restClient) {
		this.restClient=restClient;
	}
	public List<Object> getexternalData(){
		log.info("fetching data from  external api");
		
		return restClient.get().uri("https://jsonplaceholder.typicode.com/posts")
				.retrieve().body(new ParameterizedTypeReference<List<Object>>() {}
				);
	}

}
