package co.edu.unbosque.tallersicseven.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.tallersicseven.dto.PokemonDTO;
import co.edu.unbosque.tallersicseven.dto.PokemonListaDTO;
import co.edu.unbosque.tallersicseven.dto.PokemonResumenDTO;
import co.edu.unbosque.tallersicseven.exception.ExternalApiException;
import co.edu.unbosque.tallersicseven.service.PokemonService;

@RestController
@RequestMapping("/api/pokemon")
public class PokemonController {

	private final PokemonService pokemonService;

	public PokemonController(PokemonService pokemonService) {
		this.pokemonService = pokemonService;
	}

	/**
	 * GET /api/pokemon/alazar
	 */
	@GetMapping("/alazar")
	public ResponseEntity<?> pokemonAlAzar() {
		try {
			PokemonDTO poke = pokemonService.pokemonAlAzar();
			return ResponseEntity.status(HttpStatus.OK).body(poke);

		} catch ( RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
					.body(Map.of("error", "Error al obtener un Pokémon al azar: " + e.getMessage()));
		}
	}

	/**
	 * GET /api/pokemon/equipo/alazar
	 */
	@GetMapping("/equipo/alazar")
	public ResponseEntity<?> equipoAlAzar() {
		try {
			List<PokemonResumenDTO> equipo = pokemonService.equipoAlAzar();
			return ResponseEntity.status(HttpStatus.OK).body(equipo);

		} catch ( RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
					.body(Map.of("error", "Error al generar el equipo al azar: " + e.getMessage()));
		}
	}

	/**
	 * GET /api/pokemon/{nombreOId}
	 */
	@GetMapping("/{nombreOId}")
	public ResponseEntity<?> obtenerPokemon(@PathVariable String nombreOId) {
		try {
			PokemonDTO pokemon = pokemonService.obtenerPokemon(nombreOId);
			return ResponseEntity.status(HttpStatus.OK).body(pokemon);

		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(Map.of("error", e.getMessage()));

		} catch ( RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(Map.of("error", e.getMessage()));
		}
	}

	/**
	 * GET /api/pokemon?limit=20&offset=0
	 */
	@GetMapping
	public ResponseEntity<?> listarPokemones(
			@RequestParam(defaultValue = "20") int limit,
			@RequestParam(defaultValue = "0") int offset) {
		try {
			PokemonListaDTO lista = pokemonService.listarPokemones(limit, offset);
			return ResponseEntity.status(HttpStatus.OK).body(lista);

		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(Map.of("error", e.getMessage()));

		} catch (  RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
					.body(Map.of("error", "Error al comunicarse con la API externa: " + e.getMessage()));
		}
	}

	/**
	 * GET /api/pokemon/{nombreOId}/sprite
	 */
	@GetMapping(value = "/{nombreOId}/sprite", produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<?> obtenerSprite(@PathVariable String nombreOId) {
		try {
			byte[] imagen = pokemonService.obtenerSpritePokemon(nombreOId);
			return ResponseEntity.status(HttpStatus.OK)
					.contentType(MediaType.IMAGE_PNG)
					.body(imagen);

		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.contentType(MediaType.APPLICATION_JSON)
					.body(Map.of("error", e.getMessage()));

		} catch ( RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.contentType(MediaType.APPLICATION_JSON)
					.body(Map.of("error", e.getMessage()));
		}
	}
}