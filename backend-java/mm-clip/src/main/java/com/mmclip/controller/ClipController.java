package com.mmclip.controller;

import com.mmclip.client.ClipClient;

public class ClipController {
	
	public static void run() throws Exception {
		
		String imagePath = "test.jpg";
		String text = "A dog playing in the park";

		double similarity = ClipClient.getSimilarity(imagePath, text);
		System.out.println("Similarity Score: " + similarity);
	}
}