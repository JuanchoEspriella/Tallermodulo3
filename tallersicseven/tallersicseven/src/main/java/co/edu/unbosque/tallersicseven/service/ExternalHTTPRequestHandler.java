package co.edu.unbosque.tallersicseven.service;

import java.io.IOException;
import java.io.File;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

import java.net.http.HttpClient;

import java.net.http.HttpRequest;

import java.net.http.HttpResponse;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import co.edu.unbosque.tallersicseven.dto.CountriesDTO;

import com.google.gson.Gson;

import com.google.gson.GsonBuilder;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import co.edu.unbosque.tallersicseven.dto.PokemonDTO;
import co.edu.unbosque.tallersicseven.exception.ExternalApiException;

public class ExternalHTTPRequestHandler {

	private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2)
			.connectTimeout(Duration.ofSeconds(10)).build();

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

	public static <T> T doGetAndConvertToDto(String url, Class<T> responseType) {
	    HttpRequest request = HttpRequest.newBuilder()
	            .GET()
	            .uri(URI.create(url))
	            .header("Accept", "application/json")
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


	public static File doGetFile(String url, Path destination) {
		HttpRequest request = HttpRequest.newBuilder()
				.GET()
				.uri(URI.create(url))
				.timeout(Duration.ofSeconds(30))
				.header("Accept", "image/*")
				.build();

		try {
			HttpResponse<byte[]> response = HTTP_CLIENT.send(
					request, HttpResponse.BodyHandlers.ofByteArray());

			if (response.statusCode() < 200 || response.statusCode() >= 300) {
				throw new ExternalApiException("Error al descargar la imagen. Código HTTP: " + response.statusCode());
			}

			Path destinoAbsoluto = destination.toAbsolutePath().normalize();
			Files.createDirectories(destinoAbsoluto.getParent());
			Files.write(destinoAbsoluto, response.body());
			return destinoAbsoluto.toFile();
		} catch (IOException exception) {
			throw new ExternalApiException("No fue posible guardar la imagen descargada.", exception);
		} catch (InterruptedException exception) {
			Thread.currentThread().interrupt();
			throw new ExternalApiException("La descarga de la imagen fue interrumpida.", exception);
		}
	}

	public static List<CountriesDTO> doGetAndConvertToDtoListCountries(String url) {
		HttpRequest request = HttpRequest.newBuilder()
				.GET()
				.uri(URI.create(url))
				.timeout(Duration.ofSeconds(15))
				.header("Accept", "application/json")
				.build();

		try {
			HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

			if (response.statusCode() == 404) {
				return new ArrayList<>();
			}

			if (response.statusCode() != 200) {
				throw new ExternalApiException(
						"Error al consultar countries.dev. Código HTTP: " + response.statusCode());
			}

			JsonElement root = JsonParser.parseString(response.body());
			List<CountriesDTO> paises = new ArrayList<>();

			if (root.isJsonArray()) {
				JsonArray elementos = root.getAsJsonArray();
				for (JsonElement elemento : elementos) {
					if (elemento.isJsonObject()) {
						paises.add(convertirAPais(elemento.getAsJsonObject()));
					}
				}
			} else if (root.isJsonObject()) {
				paises.add(convertirAPais(root.getAsJsonObject()));
			} else {
				throw new ExternalApiException("La API de países devolvió un formato JSON inesperado.");
			}

			return paises;
		} catch (IOException exception) {
			throw new ExternalApiException("No fue posible conectar con countries.dev.", exception);
		} catch (InterruptedException exception) {
			Thread.currentThread().interrupt();
			throw new ExternalApiException("La consulta a countries.dev fue interrumpida.", exception);
		} catch (JsonParseException | IllegalStateException exception) {
			throw new ExternalApiException("No fue posible interpretar la respuesta de countries.dev.", exception);
		}
	}

	private static CountriesDTO convertirAPais(JsonObject json) {
		CountriesDTO pais = new CountriesDTO();
		pais.setName(obtenerTexto(json, "name"));
		pais.setCapital(obtenerTextoFlexible(json.get("capital")));
		pais.setPopulation(obtenerLong(json, "population"));
		pais.setRegion(obtenerTexto(json, "region"));
		pais.setSubregion(obtenerTexto(json, "subregion"));
		pais.setTimezones(String.join(", ", obtenerListaTexto(json.get("timezones"))));

		JsonObject flags = obtenerObjeto(json.get("flags"));
		String bandera = obtenerTexto(flags, "png");
		if (bandera == null) {
			bandera = obtenerTexto(json, "flag");
		}
		pais.setFlag(bandera);

		pais.setLanguages(String.join(", ", obtenerNombresDeObjetos(json.get("languages"), "name")));
		pais.setCurrencies(String.join(", ", obtenerMonedas(json.get("currencies"))));
		return pais;
	}

	private static String obtenerTexto(JsonObject objeto, String propiedad) {
		if (objeto == null) {
			return null;
		}
		JsonElement elemento = objeto.get(propiedad);
		return elemento != null && elemento.isJsonPrimitive() ? elemento.getAsString() : null;
	}

	private static long obtenerLong(JsonObject objeto, String propiedad) {
		if (objeto == null) {
			return 0L;
		}
		JsonElement elemento = objeto.get(propiedad);
		try {
			return elemento != null && elemento.isJsonPrimitive() ? elemento.getAsLong() : 0L;
		} catch (NumberFormatException exception) {
			return 0L;
		}
	}

	private static JsonObject obtenerObjeto(JsonElement elemento) {
		return elemento != null && elemento.isJsonObject() ? elemento.getAsJsonObject() : null;
	}

	private static String obtenerTextoFlexible(JsonElement elemento) {
		if (elemento == null || elemento.isJsonNull()) {
			return null;
		}
		if (elemento.isJsonPrimitive()) {
			return elemento.getAsString();
		}
		if (elemento.isJsonArray() && !elemento.getAsJsonArray().isEmpty()) {
			JsonElement primero = elemento.getAsJsonArray().get(0);
			return primero.isJsonPrimitive() ? primero.getAsString() : null;
		}
		return null;
	}

	private static List<String> obtenerListaTexto(JsonElement elemento) {
		List<String> valores = new ArrayList<>();
		if (elemento != null && elemento.isJsonArray()) {
			for (JsonElement item : elemento.getAsJsonArray()) {
				if (item.isJsonPrimitive()) {
					valores.add(item.getAsString());
				}
			}
		}
		return valores;
	}

	private static List<String> obtenerNombresDeObjetos(JsonElement elemento, String propiedad) {
		List<String> valores = new ArrayList<>();
		if (elemento != null && elemento.isJsonArray()) {
			for (JsonElement item : elemento.getAsJsonArray()) {
				JsonObject objeto = obtenerObjeto(item);
				String valor = obtenerTexto(objeto, propiedad);
				if (valor != null && !valor.isBlank()) {
					valores.add(valor);
				}
			}
		} else if (elemento != null && elemento.isJsonObject()) {
			for (Map.Entry<String, JsonElement> entrada : elemento.getAsJsonObject().entrySet()) {
				if (entrada.getValue().isJsonPrimitive()) {
					valores.add(entrada.getValue().getAsString());
				}
			}
		}
		return valores;
	}

	private static List<String> obtenerMonedas(JsonElement elemento) {
		List<String> monedas = new ArrayList<>();
		if (elemento != null && elemento.isJsonArray()) {
			for (JsonElement item : elemento.getAsJsonArray()) {
				JsonObject objeto = obtenerObjeto(item);
				String nombre = obtenerTexto(objeto, "name");
				String codigo = obtenerTexto(objeto, "code");
				if (nombre != null) {
					monedas.add(codigo == null ? nombre : nombre + " (" + codigo + ")");
				}
			}
		} else if (elemento != null && elemento.isJsonObject()) {
			for (Map.Entry<String, JsonElement> entrada : elemento.getAsJsonObject().entrySet()) {
				JsonObject objeto = obtenerObjeto(entrada.getValue());
				String nombre = obtenerTexto(objeto, "name");
				if (nombre != null) {
					monedas.add(nombre + " (" + entrada.getKey() + ")");
				}
			}
		}
		return monedas;
	}

	public static String prettyPrintUsingGson(String uglyJson) {
		Gson gson = new GsonBuilder().setLenient().setPrettyPrinting().create();
		JsonElement jsonElement = JsonParser.parseString(uglyJson);
		String prettyJsonString = gson.toJson(jsonElement);
		return prettyJsonString;
	}

}
