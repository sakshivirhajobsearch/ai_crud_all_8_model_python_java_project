package com.mmclip;

import java.io.File;
import java.util.Map;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ClipClient {
	public static double getSimilarity(String imagePath, String text) throws Exception {
		String url = "http://localhost:5003/clip/similarity";

		try (CloseableHttpClient client = HttpClients.createDefault()) {
			HttpPost post = new HttpPost(url);

			HttpEntity entity = MultipartEntityBuilder.create().addTextBody("text", text, ContentType.TEXT_PLAIN)
					.addBinaryBody("image", new File(imagePath), ContentType.DEFAULT_BINARY, imagePath).build();

			post.setEntity(entity);

			return client.execute(post, response -> {
				String json = EntityUtils.toString(response.getEntity());
				ObjectMapper mapper = new ObjectMapper();
				Map<String, Object> map = mapper.readValue(json, Map.class);
				return (double) map.get("similarity");
			});
		}
	}
}