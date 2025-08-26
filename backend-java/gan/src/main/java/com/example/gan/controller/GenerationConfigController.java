package com.example.gan.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gan.model.GenerationConfig;
import com.example.gan.service.GenerationService;

@RestController
@RequestMapping("/api/gan")
public class GenerationConfigController {

	private final Map<Long, GenerationConfig> configStore = new HashMap<>();
	private long currentId = 1;

	@Autowired
	private GenerationService generationService;

	// Create config
	@PostMapping("/config")
	public GenerationConfig createConfig(@RequestBody GenerationConfig config) {
		config.setId(currentId++);
		configStore.put(config.getId(), config);
		return config;
	}

	// Read all configs
	@GetMapping("/config")
	public Collection<GenerationConfig> getAllConfigs() {
		return configStore.values();
	}

	// Read single config
	@GetMapping("/config/{id}")
	public GenerationConfig getConfigById(@PathVariable Long id) {
		return configStore.get(id);
	}

	// Update config
	@PutMapping("/config/{id}")
	public GenerationConfig updateConfig(@PathVariable Long id, @RequestBody GenerationConfig updatedConfig) {
		GenerationConfig existing = configStore.get(id);
		if (existing != null) {
			existing.setPrompt(updatedConfig.getPrompt());
			existing.setSteps(updatedConfig.getSteps());
			return existing;
		}
		return null;
	}

	// Delete config
	@DeleteMapping("/config/{id}")
	public void deleteConfig(@PathVariable Long id) {
		configStore.remove(id);
	}

	// Trigger image generation
	@PostMapping("/generate/{id}")
	public String generate(@PathVariable Long id) {
		GenerationConfig config = configStore.get(id);
		if (config != null) {
			return generationService.generateImage(config);
		}
		return "Config not found with id: " + id;
	}
}