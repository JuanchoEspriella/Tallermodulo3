package co.edu.unbosque.tallersicseven.service;

import java.io.IOException;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;

import java.net.http.HttpRequest;

import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

import com.google.gson.GsonBuilder;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import co.edu.unbosque.tallersicseven.dto.PokemonDTO;
import java.util.ArrayList;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import co.edu.unbosque.tallersicseven.dto.CountriesDTO;
import java.util.Scanner;

public class ExternalHTTPRequestHandler {

	private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2)
			.connectTimeout(Duration.ofSeconds(10)).build();

	public static void main(String[] args) {
		
		

		Scanner sc = new Scanner(System.in);

		System.out.println("Escribe el nombre de un pais:");
		String nombre = sc.nextLine();

		String url = "https://countries.dev/name/" + codificar(nombre);

		List<CountriesDTO> paises = doGetAndConvertToDtoListCountries(url);

		if (paises != null && !paises.isEmpty()) {

			for (CountriesDTO pais : paises) {
				System.out.println(pais);
			}

		} else {
			System.out.println("No se encontro el pais");
		}

		sc.close();
	}

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

	public static <T> T doGetAndConvertToDto(String url, Class<T> responseType) {
		HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).header("Accept", "application/json")
				.build();

		try {
			HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

			if (response.statusCode() != 200) {
				throw new RuntimeException("Error al consultar la API externa. Código HTTP: " + response.statusCode());
			}

			return new GsonBuilder().create().fromJson(response.body(), responseType);

		} catch (IOException e) {
			throw new RuntimeException("Error de conexión con la API externa", e);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new RuntimeException("La petición HTTP fue interrumpida", e);
		}
	}

	// here

	public static List<CountriesDTO> doGetAndConvertToDtoListCountries(String url) {

		HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).header("Accept", "application/json")
				.timeout(Duration.ofSeconds(15)).build();

		HttpResponse<String> response = null;

		try {

			response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

		} catch (IOException e) {

			e.printStackTrace();

		} catch (InterruptedException e) {

			Thread.currentThread().interrupt();
			e.printStackTrace();
		}

		if (response == null) {
			return null;
		}

		List<CountriesDTO> lista = new ArrayList<>();

		if (response.statusCode() == 404) {
			return lista;
		}

		if (response.statusCode() != 200) {
			return null;
		}

		try {

			JsonElement root = JsonParser.parseString(response.body());

			if (root.isJsonArray()) {

				for (JsonElement el : root.getAsJsonArray()) {

					if (el.isJsonObject()) {

						lista.add(convertirAPais(el.getAsJsonObject()));
					}
				}

			} else if (root.isJsonObject()) {

				lista.add(convertirAPais(root.getAsJsonObject()));
			}

		} catch (Exception e) {

			System.out.println("Respuesta inesperada: " + response.body());
			e.printStackTrace();
			return null;
		}

		return lista;
	}

	private static CountriesDTO convertirAPais(JsonObject json) {

		CountriesDTO pais = new CountriesDTO();

		pais.setName(getString(json, "name"));
		pais.setCapital(getString(json, "capital"));
		pais.setPopulation(getLong(json, "population"));
		pais.setRegion(getString(json, "subregion"));
		pais.setContinents(getString(json, "region"));

		pais.setTimezones(String.join(", ", getStringList(json, "timezones")));

		JsonObject bandera = json.getAsJsonObject("flags");
		pais.setFlag(getString(bandera, "png"));

		List<String> idiomas = new ArrayList<>();

		JsonElement langs = json.get("languages");

		if (langs != null && langs.isJsonArray()) {

			for (JsonElement item : langs.getAsJsonArray()) {

				String idioma = getString(item.getAsJsonObject(), "name");
				idiomas.add(idioma);
			}
		}

		pais.setLanguages(String.join(", ", idiomas));

		List<String> monedas = new ArrayList<>();

		JsonElement currs = json.get("currencies");

		if (currs != null && currs.isJsonArray()) {

			for (JsonElement item : currs.getAsJsonArray()) {

				JsonObject moneda = item.getAsJsonObject();

				String nombre = getString(moneda, "name");
				String codigo = getString(moneda, "code");

				monedas.add(nombre + " (" + codigo + ")");
			}
		}

		pais.setCurrencies(String.join(", ", monedas));

		return pais;
	}

	// to here

	public static String codificar(String texto) {
		return URLEncoder.encode(texto.trim(), StandardCharsets.UTF_8).replace("+", "%20");
	}

	private static String getString(JsonObject obj, String key) {
		JsonElement el = obj.get(key);
		return (el != null && el.isJsonPrimitive()) ? el.getAsString() : null;
	}

	private static long getLong(JsonObject obj, String key) {
		JsonElement el = obj.get(key);
		return (el != null && el.isJsonPrimitive()) ? el.getAsLong() : 0;
	}

	private static double getDouble(JsonObject obj, String key) {
		JsonElement el = obj.get(key);
		return (el != null && el.isJsonPrimitive()) ? el.getAsDouble() : 0;
	}

	private static List<String> getStringList(JsonObject obj, String key) {
		List<String> lista = new ArrayList<>();
		JsonElement el = obj.get(key);
		if (el != null && el.isJsonArray()) {
			for (JsonElement item : el.getAsJsonArray()) {
				if (item.isJsonPrimitive()) {
					lista.add(item.getAsString());
				}
			}
		}
		return lista;
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