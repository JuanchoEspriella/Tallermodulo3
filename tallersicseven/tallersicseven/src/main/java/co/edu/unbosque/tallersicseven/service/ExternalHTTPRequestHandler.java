package co.edu.unbosque.tallersicseven.service;

import java.io.IOException;

import java.net.URI;

import java.net.http.HttpClient;

import java.net.http.HttpRequest;

import java.net.http.HttpResponse;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

import com.google.gson.GsonBuilder;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import co.edu.unbosque.tallersicseven.dto.PokemonDTO;

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
//	public static void main(String[] args) throws IOException, InterruptedException {
//		String fact = "https://uselessfacts.jsph.pl/api/v2/facts/random";
//	}

	public static List<PokemonDTO> doGetAndConvertToDtoList(String url) {
		HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url))
				.header("Content-Type", "application/json").build();

		HttpResponse<String> response = null;
		try {
			response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String json = response.body();
		List<PokemonDTO> list = Arrays.asList(new GsonBuilder().create().fromJson(json, PokemonDTO.class));

		return list;

	}

	public static PokemonDTO doGetAndConvertToDto(String url) {
		HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url))
				.header("Content-Type", "application/json").build();

		HttpResponse<String> response = null;
		try {
			response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String json = response.body();
		PokemonDTO fact = new GsonBuilder().create().fromJson(json, PokemonDTO.class);

		return fact;

	}

	public static String doGetAndParse(String url) {
		HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url))
				.header("Content-Type", "application/json").build();

		HttpResponse<String> response = null;
		try {
			response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("status code -> " + response.statusCode());

		String uglyJson = response.body();

		return prettyPrintUsingGson(uglyJson);
	}

	public static String doGetWithHeaders(String url) {
		HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url))
				.header("Content-Type", "application/json")
				.setHeader("X-Apikey", "b022b08d83bbbfd856d2f1e6080a08cc93a24c38db6537639d2c0b5bd84c0701").build();

		HttpResponse<String> response = null;
		try {
			response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("status code -> " + response.statusCode());

		String uglyJson = response.body();
		return prettyPrintUsingGson(uglyJson);
	}

	public static String doGet(String url) {
		HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url))
				.header("Content-Type", "application/json").build();

		HttpResponse<String> response = null;
		try {
			response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("status code -> " + response.statusCode());

		String uglyJson = response.body();
		return prettyPrintUsingGson(uglyJson);
	}

	public static String doPost(String url, String json) {
		HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(json))
				.uri(URI.create(url)).header("Content-Type", "application/json").build();

		HttpResponse<String> response = null;
		try {
			response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("status code -> " + response.statusCode());

		return prettyPrintUsingGson(response.body());
	}

	public static String doPut(String url, String json) {

		// add json header
		HttpRequest request = HttpRequest.newBuilder().PUT(HttpRequest.BodyPublishers.ofString(json))
				.uri(URI.create(url)).setHeader("User-Agent", "Java 11 HttpClient Bot")
				.header("Content-Type", "application/json").build();

		HttpResponse<String> response = null;
		try {
			response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("status code -> " + response.statusCode());
		System.out.println(response.body());
		return prettyPrintUsingGson(response.body());
	}

	public static String doDelete(String url) {

		HttpRequest request = HttpRequest.newBuilder().DELETE().uri(URI.create(url))
				.setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
				.header("Content-Type", "application/json").build();

		HttpResponse<String> response = null;
		try {
			response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("status code -> " + response.statusCode());

		return prettyPrintUsingGson(response.body());
	}

	public static String prettyPrintUsingGson(String uglyJson) {
		Gson gson = new GsonBuilder().setLenient().setPrettyPrinting().create();
		JsonElement jsonElement = JsonParser.parseString(uglyJson);
		String prettyJsonString = gson.toJson(jsonElement);
		return prettyJsonString;
	}

}
