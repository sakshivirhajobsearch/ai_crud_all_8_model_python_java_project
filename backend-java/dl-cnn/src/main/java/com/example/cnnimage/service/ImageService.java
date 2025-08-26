package com.example.cnnimage.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

	public String predict(MultipartFile file) {
		
		try {
			File tempFile = File.createTempFile("img-", file.getOriginalFilename());
			file.transferTo(tempFile);

			ProcessBuilder pb = new ProcessBuilder("python", "python/predict.py", tempFile.getAbsolutePath());
			pb.redirectErrorStream(true);
			Process process = pb.start();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String output = reader.readLine();
			process.waitFor();
			tempFile.delete();

			return output;
		} catch (Exception e) {
			return "Error: " + e.getMessage();
		}
	}
}