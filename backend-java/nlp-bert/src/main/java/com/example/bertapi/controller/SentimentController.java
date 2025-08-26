package com.example.bertapi.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.bertapi.model.SentimentRequest;
import com.example.bertapi.model.SentimentResponse;

@RestController
@RequestMapping("/api/sentiment")
public class SentimentController {

	private final String PYTHON_API_URL = "http://localhost:5000/analyze";

	@PostMapping
	public ResponseEntity<?> analyzeSentiment(@RequestBody SentimentRequest request) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<SentimentRequest> entity = new HttpEntity<>(request, headers);

		try {
			ResponseEntity<SentimentResponse[]> response = restTemplate.postForEntity(PYTHON_API_URL, entity,
					SentimentResponse[].class);

			return ResponseEntity.ok(response.getBody());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error analyzing sentiment: " + e.getMessage());
		}
	}
}