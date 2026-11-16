package co.edu.unbosque.tallersicseven.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import co.edu.unbosque.tallersicseven.dto.PokemonDTO;
import co.edu.unbosque.tallersicseven.dto.PokemonListaDTO;
import co.edu.unbosque.tallersicseven.dto.PokemonResumenDTO;
import co.edu.unbosque.tallersicseven.exception.ExternalApiException;

@Service
public class PokemonService {

	@Value("${external.pokemon.base-url}")
	private String baseUrl;

	@Value("${external.pokemon.endpoint.detalle}")
	private String endpointDetalle;

	@Value("${external.pokemon.endpoint.lista}")
	private String endpointLista;

	/**
	 * Consulta un Pokémon por su nombre o identificador.
	 */
	public PokemonDTO obtenerPokemon(String nombreOId) {
		if (nombreOId == null || nombreOId.trim().isEmpty()) {
			throw new IllegalArgumentException("Debes proporcionar el nombre o ID del Pokémon.");
		}

		String identificador = nombreOId.trim().toLowerCase(Locale.ROOT);

		if (!identificador.matches("[a-z0-9-]+")) {
			throw new IllegalArgumentException("El nombre o ID del Pokémon no tiene un formato válido.");
		}

		String endpoint = endpointDetalle.replace("{nombreOId}", identificador);
		String url = baseUrl + endpoint;

		return ExternalHTTPRequestHandler.doGetAndConvertToDto(url, PokemonDTO.class);
	}

	/**
	 * Consulta el listado paginado de Pokémon.
	 */
	public PokemonListaDTO listarPokemones(int limit, int offset) {
		if (limit < 1 || limit > 50) {
			throw new IllegalArgumentException("El límite debe estar entre 1 y 50.");
		}

		if (offset < 0) {
			throw new IllegalArgumentException("El offset no puede ser negativo.");
		}

		String url = baseUrl + endpointLista + "?limit=" + limit + "&offset=" + offset;

		return ExternalHTTPRequestHandler.doGetAndConvertToDto(url, PokemonListaDTO.class);
	}

	/**
	 * Descarga el sprite principal del Pokémon usando doGetFile.
	 */
	public byte[] obtenerSpritePokemon(String nombreOId) {
		PokemonDTO pokemon = obtenerPokemon(nombreOId);

		if (pokemon.getSprites() == null || pokemon.getSprites().getFront_default() == null) {
			throw new ExternalApiException("El Pokémon '" + nombreOId + "' no tiene un sprite disponible.");
		}

		String spriteUrl = pokemon.getSprites().getFront_default();
		String nombreArchivo = pokemon.getName() + ".png";
		Path destino = Paths.get(System.getProperty("java.io.tmpdir"), "pokemon-sprites", nombreArchivo);

		File imagenDescargada = ExternalHTTPRequestHandler.doGetFile(spriteUrl, destino);

		try {
			return Files.readAllBytes(imagenDescargada.toPath());
		} catch (IOException e) {
			throw new ExternalApiException("Error al leer el archivo del sprite descargado.", e);
		}
	}

	/**
	 * Obtiene un Pokémon completamente al azar (ID entre 1 y 1025).
	 */
	public PokemonDTO pokemonAlAzar() {
		String id = String.valueOf(ThreadLocalRandom.current().nextInt(1, 1026));
		return obtenerPokemon(id);
	}

	/**
	 * Genera un equipo al azar de 6 Pokémon usando PokemonResumenDTO y ModelMapper.
	 */
	public List<PokemonResumenDTO> equipoAlAzar() {
		List<PokemonResumenDTO> equipo = new ArrayList<>();
		Set<Integer> idsUsados = new HashSet<>();
		ModelMapper modelMapper = new ModelMapper();

		while (equipo.size() < 6) {
			int idAleatorio = ThreadLocalRandom.current().nextInt(1, 1026);

			if (!idsUsados.contains(idAleatorio)) {
				idsUsados.add(idAleatorio);
				try {
					PokemonDTO pokemon = obtenerPokemon(String.valueOf(idAleatorio));
					PokemonResumenDTO resumen = modelMapper.map(pokemon, PokemonResumenDTO.class);
					equipo.add(resumen);
				} catch (Exception e) {
					// Ignora fallos puntuales si un ID falla en la red
				}
			}
		}

		return equipo;
	}
}