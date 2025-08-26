package com.example.gan.service;

import org.springframework.stereotype.Service;

import com.example.gan.config.PythonRunner;
import com.example.gan.model.GenerationConfig;

@Service
public class GenerationService {

	public String generateImage(GenerationConfig config) {
		// Example: Call Python script with prompt and steps
		String scriptPath = "gan/scripts/generate_image.py"; // adjust path if needed

		// Convert steps to string
		String steps = String.valueOf(config.getSteps());
		String prompt = config.getPrompt();

		return PythonRunner.run(scriptPath, prompt, steps);
	}
}