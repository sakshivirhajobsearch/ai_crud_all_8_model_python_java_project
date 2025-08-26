package com.example.ml.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PredictionService {
	public double callPythonAPI(double area) {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:5000/predict";

		Map<String, Object> request = new HashMap<>();
		request.put("area", area);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

		ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
		return Double.parseDouble(response.getBody().get("predicted_price").toString());
	}
}