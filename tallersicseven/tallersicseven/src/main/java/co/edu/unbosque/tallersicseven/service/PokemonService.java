package co.edu.unbosque.tallersicseven.service;

import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import co.edu.unbosque.tallersicseven.dto.PokemonDTO;
import co.edu.unbosque.tallersicseven.dto.PokemonListaDTO;

@Service
public class PokemonService {

	@Value("${external.pokemon.base-url}")
	private String baseUrl;

	@Value("${external.pokemon.endpoint.detalle}")
	private String endpointDetalle;

	@Value("${external.pokemon.endpoint.lista}")
	private String endpointLista;

	/**
	 * Consulta un Pokemon por su nombre o identificador.
	 *
	 * @param nombreOId Nombre o ID del Pokemon.
	 * @return Informacion detallada del Pokemon.
	 */
	public PokemonDTO obtenerPokemon(String nombreOId) {

		// Validar que el parametro no este vacio
		if (nombreOId == null || nombreOId.trim().isEmpty()) {
			throw new IllegalArgumentException("Debes proporcionar el nombre o ID del Pokemon.");
		}

		String identificador = nombreOId.trim().toLowerCase(Locale.ROOT);

		// Validar caracteres permitidos
		if (!identificador.matches("[a-z0-9-]+")) {
			throw new IllegalArgumentException("El nombre o ID del Pokemon no tiene un formato valido.");
		}

		// Construir la URL
		String endpoint = endpointDetalle.replace("{nombreOId}", identificador);

		String url = baseUrl + endpoint;

		// Consultar la API y convertir la respuesta al DTO
		return ExternalHTTPRequestHandler.doGetAndConvertToDto(url, PokemonDTO.class);
	}

	/**
	 * Consulta el listado paginado de Pokemon.
	 *
	 * @param limit  Cantidad de Pokemon por pagina.
	 * @param offset Posicion desde donde comienza la consulta.
	 * @return Listado paginado de Pokemon.
	 */
	public PokemonListaDTO listarPokemones(int limit, int offset) {

		// Validar la cantidad solicitada
		if (limit < 1 || limit > 50) {
			throw new IllegalArgumentException("El limite debe estar entre 1 y 50.");
		}

		// Validar el desplazamiento
		if (offset < 0) {
			throw new IllegalArgumentException("El offset no puede ser negativo.");
		}

		// Construir la URL
		String url = baseUrl + endpointLista + "?limit=" + limit + "&offset=" + offset;

		// Consultar la API y convertir la respuesta al DTO
		return ExternalHTTPRequestHandler.doGetAndConvertToDto(url, PokemonListaDTO.class);
	}
<<<<<<< HEAD
=======
	
	
	
	public PokemonDTO pokemonAleatorio() {
		
		int id = ThreadLocalRandom.current().nextInt(1, 1026);
		
		String idNew = id + "";
		
		return obtenerPokemon(idNew);
		
	}
	
>>>>>>> f69b7ad (Configure OpenAPI for API documentation and add random Pokemon service)

}
