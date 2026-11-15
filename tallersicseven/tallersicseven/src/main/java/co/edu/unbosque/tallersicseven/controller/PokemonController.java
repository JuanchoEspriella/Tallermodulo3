package co.edu.unbosque.tallersicseven.controller;

import java.net.ResponseCache;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.tallersicseven.dto.PokemonDTO;
import co.edu.unbosque.tallersicseven.service.PokemonService;

@RestController
@RequestMapping("api/pokemon")
public class PokemonController {

	private final PokemonService pokemonService;

	public PokemonController(PokemonService pokemonService) {
		this.pokemonService = pokemonService;
	}

	@GetMapping("/{nombreOId}")
	public ResponseEntity<?> obtenerPokemon(@PathVariable String nombreOId) {
		try {
			PokemonDTO pokemon = pokemonService.obtenerPokemon(nombreOId);
			return ResponseEntity.ok(pokemon);
		} catch (IllegalArgumentException e) {
			// Error de validación del usuario (ej. caracteres inválidos) -> 400 Bad Request
			return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));

		} catch (RuntimeException e) {
			// Error al consultar la API externa (ej. Pokémon no encontrado) -> 404 Not
			// Found
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
		}
	}
}
