package com.example.ml.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.ml.model.LinearRegressionParams;
import com.example.ml.repository.ParamRepository;
import com.example.ml.service.PredictionService;

@RestController
@RequestMapping("/api/linear")
public class PredictionController {

	@Autowired
	private ParamRepository repository;

	@Autowired
	private PredictionService service;

	@PostMapping("/predict")
	public String predict(@RequestBody LinearRegressionParams param) {
		repository.save(param);
		double result = service.callPythonAPI(param.getArea());
		return "Predicted Price: " + result;
	}

	@GetMapping("/params")
	public Iterable<LinearRegressionParams> getAllParams() {
		return repository.findAll();
	}
}