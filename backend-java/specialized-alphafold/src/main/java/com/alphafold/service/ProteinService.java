package com.alphafold.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;

import com.alphafold.model.ProteinSequence;

@Service
public class ProteinService {

	public String getPredictedStructure(ProteinSequence protein) {
		try {
			URL url = new URL("http://localhost:5005/predict");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);

			String jsonInputString = String.format("{\"sequence\": \"%s\"}", protein.getSequence());

			try (OutputStream os = conn.getOutputStream()) {
				byte[] input = jsonInputString.getBytes("utf-8");
				os.write(input, 0, input.length);
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			StringBuilder response = new StringBuilder();
			String responseLine;

			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}

			return response.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "Error predicting protein structure.";
		}
	}
}