package com.example.yolo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.yolo.service.YoloService;

@RestController
@RequestMapping("/yolo")
public class YoloController {

	@Autowired
	private YoloService yoloService;

	@PostMapping("/detect")
	public ResponseEntity<String> detect(@RequestParam("file") MultipartFile file)
			throws IOException, InterruptedException {
		String result = yoloService.detectObjects(file);
		return ResponseEntity.ok(result);
	}
}