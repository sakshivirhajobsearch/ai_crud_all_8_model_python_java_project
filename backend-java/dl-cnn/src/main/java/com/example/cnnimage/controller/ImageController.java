package com.example.cnnimage.controller;

import com.example.cnnimage.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/images")
public class ImageController {

	@Autowired
	private ImageService imageService;

	@PostMapping("/predict")
	public ResponseEntity<String> predict(@RequestParam("image") MultipartFile file) {
		String result = imageService.predict(file);
		return ResponseEntity.ok(result);
	}
}