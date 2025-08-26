package com.rlqlearning.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.stereotype.Service;

@Service
public class AgentService {
	
	public String getNextAction(int x, int y) {
		try {
			ProcessBuilder pb = new ProcessBuilder("python", "python/predict_qlearning.py", String.valueOf(x),
					String.valueOf(y));
			Process process = pb.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			return reader.readLine();
		} catch (Exception e) {
			e.printStackTrace();
			return "ERROR";
		}
	}
}
