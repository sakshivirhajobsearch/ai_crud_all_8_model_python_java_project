package com.example.yolo.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class YoloService {

	public String detectObjects(MultipartFile file) throws IOException, InterruptedException {
		String imageName = UUID.randomUUID().toString() + ".jpg";
		File imageFile = new File("uploaded_" + imageName);
		file.transferTo(imageFile);

		ProcessBuilder builder = new ProcessBuilder("python3", "python/detect.py", imageFile.getAbsolutePath());
		builder.redirectErrorStream(true);
		Process process = builder.start();

		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		StringBuilder output = new StringBuilder();
		String line;

		while ((line = reader.readLine()) != null) {
			output.append(line);
		}

		int exitCode = process.waitFor();
		imageFile.delete(); // Cleanup

		return exitCode == 0 ? output.toString() : "{\"error\":\"Detection failed\"}";
	}
}