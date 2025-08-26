package com.example.gan.config;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class PythonRunner {
	public static String run(String scriptPath, String... args) {
		try {
			String[] command = new String[args.length + 2];
			command[0] = "python"; // or "python3" depending on OS
			command[1] = scriptPath;
			System.arraycopy(args, 0, command, 2, args.length);

			ProcessBuilder pb = new ProcessBuilder(command);
			pb.redirectErrorStream(true);
			Process process = pb.start();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			StringBuilder output = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line).append("\n");
			}

			int exitCode = process.waitFor();
			return output.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "Error running Python script: " + e.getMessage();
		}
	}
}