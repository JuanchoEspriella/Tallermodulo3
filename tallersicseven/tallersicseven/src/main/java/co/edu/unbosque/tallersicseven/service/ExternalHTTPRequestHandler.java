
package co.edu.unbosque.tallersicseven.service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class ExternalHTTPRequestHandler {

	private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2)
			.connectTimeout(Duration.ofSeconds(10)).build();

	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	public static String doGet(String url) throws IOException, InterruptedException {

		HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url))
				.header("Content-Type", "application/json").build();

		HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

		validateResponse(response.statusCode());

		return response.body();
	}

	public static String doGetWithQueryParameters(String url, Map<String, String> parameters)
			throws IOException, InterruptedException {

		StringBuilder urlBuilder = new StringBuilder(url);
		urlBuilder.append("?");

		parameters.forEach((key, value) -> {
			if (urlBuilder.charAt(urlBuilder.length() - 1) != '?') {
				urlBuilder.append("&");
			}

			urlBuilder.append(URLEncoder.encode(key, StandardCharsets.UTF_8));

			urlBuilder.append("=");

			urlBuilder.append(URLEncoder.encode(value, StandardCharsets.UTF_8));
		});

		return doGet(urlBuilder.toString());
	}

	public static <T> T doGetAndConvertToDto(String url, Class<T> dtoClass) throws IOException, InterruptedException {

		String json = doGet(url);

		return GSON.fromJson(json, dtoClass);
	}

	public static <T> List<T> doGetAndConvertToDtoList(String url, Type listType)
			throws IOException, InterruptedException {

		String json = doGet(url);

		return GSON.fromJson(json, listType);
	}

	public static File doGetFile(String url, Path destination) throws IOException, InterruptedException {

		HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).timeout(Duration.ofSeconds(30))
				.header("Accept", "image/*").build();

		HttpResponse<byte[]> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofByteArray());

		validateResponse(response.statusCode());

		Files.createDirectories(destination.getParent());

		Files.write(destination, response.body());

		return destination.toFile();
	}

	public static String doGetAndParse(String url) throws IOException, InterruptedException {

		String json = doGet(url);

		return prettyPrintUsingGson(json);
	}

	public static String prettyPrintUsingGson(String uglyJson) {

		JsonElement jsonElement = JsonParser.parseString(uglyJson);

		return GSON.toJson(jsonElement);
	}

	private static void validateResponse(int statusCode) throws IOException {

		if (statusCode < 200 || statusCode >= 300) {
			throw new IOException("Error en la petición HTTP: " + statusCode);
		}
	}
}
