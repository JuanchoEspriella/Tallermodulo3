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
import co.edu.unbosque.tallersicseven.service.PokemonService;

// Anotaciones de Swagger para el controlador en general
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/pokemon")
@Tag(name = "Pokémon Controller", description = "Endpoints para la gestión y consulta de Pokémon utilizando servicios externos")
public class PokemonController {

	private final PokemonService pokemonService;

	public PokemonController(PokemonService pokemonService) {
		this.pokemonService = pokemonService;
	}

	@Operation(summary = "Obtener un Pokémon al azar", description = "Consulta un Pokémon aleatorio desde la API externa y devuelve su información detallada.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Pokémon obtenido exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PokemonDTO.class))),
			@ApiResponse(responseCode = "502", description = "Error al comunicarse con la API externa o error de ejecución", content = @Content(mediaType = "application/json")) })
	@GetMapping("/alazar")
	public ResponseEntity<?> pokemonAlAzar() {
		try {
			PokemonDTO poke = pokemonService.pokemonAlAzar();
			return ResponseEntity.status(HttpStatus.OK).body(poke);

		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
					.body(Map.of("error", "Error al obtener un Pokémon al azar: " + e.getMessage()));
		}
	}

	@Operation(summary = "Generar un equipo Pokémon al azar", description = "Genera una lista con un equipo aleatorio de Pokémon resumidos.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Equipo generado exitosamente", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "502", description = "Error al generar el equipo desde la API externa", content = @Content(mediaType = "application/json")) })
	@GetMapping("/equipo/alazar")
	public ResponseEntity<?> equipoAlAzar() {
		try {
			List<PokemonResumenDTO> equipo = pokemonService.equipoAlAzar();
			return ResponseEntity.status(HttpStatus.OK).body(equipo);

		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
					.body(Map.of("error", "Error al generar el equipo al azar: " + e.getMessage()));
		}
	}

	@Operation(summary = "Obtener Pokémon por nombre o ID", description = "Busca los detalles completos de un Pokémon específico mediante su nombre o su identificador numérico.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Pokémon encontrado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PokemonDTO.class))),
			@ApiResponse(responseCode = "400", description = "Parámetro inválido o nombre/ID incorrecto", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "Pokémon no encontrado", content = @Content(mediaType = "application/json")) })
	@GetMapping("/{nombreOId}")
	public ResponseEntity<?> obtenerPokemon(
			@Parameter(description = "Nombre o ID del Pokémon a buscar", example = "pikachu") @PathVariable String nombreOId) {
		try {
			PokemonDTO pokemon = pokemonService.obtenerPokemon(nombreOId);
			return ResponseEntity.status(HttpStatus.OK).body(pokemon);

		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));

		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
		}
	}

	@Operation(summary = "Listar Pokémon paginados", description = "Devuelve una lista paginada de Pokémon utilizando los parámetros de límite y desplazamiento.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PokemonListaDTO.class))),
			@ApiResponse(responseCode = "400", description = "Parámetros de paginación inválidos", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "502", description = "Error de comunicación con la API externa", content = @Content(mediaType = "application/json")) })
	@GetMapping
	public ResponseEntity<?> listarPokemones(
			@Parameter(description = "Cantidad máxima de resultados a retornar", example = "20") @RequestParam(defaultValue = "20") int limit,
			@Parameter(description = "Número de registros omitidos desde el inicio", example = "0") @RequestParam(defaultValue = "0") int offset) {
		try {
			PokemonListaDTO lista = pokemonService.listarPokemones(limit, offset);
			return ResponseEntity.status(HttpStatus.OK).body(lista);

		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));

		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
					.body(Map.of("error", "Error al comunicarse con la API externa: " + e.getMessage()));
		}
	}

	@Operation(summary = "Obtener el sprite de un Pokémon", description = "Retorna la imagen en formato PNG correspondiente al sprite del Pokémon indicado por su nombre o ID.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Sprite obtenido exitosamente", content = @Content(mediaType = MediaType.IMAGE_PNG_VALUE)),
			@ApiResponse(responseCode = "400", description = "Parámetro inválido", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "Sprite o Pokémon no encontrado", content = @Content(mediaType = "application/json")) })
	@GetMapping(value = "/{nombreOId}/sprite", produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<?> obtenerSprite(
			@Parameter(description = "Nombre o ID del Pokémon cuyo sprite se desea obtener", example = "charizard") @PathVariable String nombreOId) {
		try {
			byte[] imagen = pokemonService.obtenerSpritePokemon(nombreOId);
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_PNG).body(imagen);

		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
					.body(Map.of("error", e.getMessage()));

		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
					.body(Map.of("error", e.getMessage()));
		}
	}
}