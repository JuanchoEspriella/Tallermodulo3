package co.edu.unbosque.tallersicseven.service;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import co.edu.unbosque.tallersicseven.dto.ImagenDTO;
import co.edu.unbosque.tallersicseven.dto.PixabayImageDTO;
import co.edu.unbosque.tallersicseven.dto.PixabayResponseDTO;
import co.edu.unbosque.tallersicseven.exception.ImagenNoEncontradaException;

@Service
public class ImagenService {

	@Value("${external.pixabay.api-key:}")
	private String apiKey;

	@Value("${external.pixabay.url:https://pixabay.com/api/}")
	private String apiUrl;

	@Value("${external.imagenes.directorio:imagenes}")
	private String directorioImagenes;

	public List<ImagenDTO> buscarImagenes(String termino) {

		String url = apiUrl
				+ "?key=" + URLEncoder.encode(apiKey, StandardCharsets.UTF_8)
				+ "&q=" + URLEncoder.encode(termino, StandardCharsets.UTF_8)
				+ "&image_type=photo"
				+ "&per_page=10";

		PixabayResponseDTO respuesta = ExternalHTTPRequestHandler
				.doGetAndConvertToDto(url, PixabayResponseDTO.class);

		List<ImagenDTO> imagenes = new ArrayList<>();

		if (respuesta == null || respuesta.getHits() == null) {
			return imagenes;
		}

		Path directorio = Path.of(directorioImagenes).toAbsolutePath().normalize();

		try {
			Files.createDirectories(directorio);
		} catch (IOException exception) {
			throw new IllegalStateException("No fue posible crear el directorio de imágenes.", exception);
		}

		for (PixabayImageDTO imagen : respuesta.getHits()) {

			String nombreArchivo = "imagen-" + imagen.getId() + ".jpg";
			Path destino = directorio.resolve(nombreArchivo);

			if (!Files.exists(destino)) {
				ExternalHTTPRequestHandler.doGetFile(imagen.getWebformatURL(), destino);
			}

			ImagenDTO imagenDTO = new ImagenDTO(
					imagen.getId(),
					nombreArchivo,
					imagen.getTags(),
					"/api/imagenes/archivo/" + nombreArchivo
			);

			imagenes.add(imagenDTO);
		}

		return imagenes;
	}

	public File obtenerArchivo(String nombreArchivo) {

		Path directorio = Path.of(directorioImagenes).toAbsolutePath().normalize();
		Path archivo = directorio.resolve(nombreArchivo).normalize();

		if (!archivo.startsWith(directorio) || !Files.isRegularFile(archivo)) {
			throw new ImagenNoEncontradaException("No se encontró la imagen solicitada.");
		}

		return archivo.toFile();
	}
	
	public byte[] obtenerBytesFotoDirecta(String termino) {
	    String url = apiUrl
	            + "?key=" + URLEncoder.encode(apiKey, StandardCharsets.UTF_8)
	            + "&q=" + URLEncoder.encode(termino, StandardCharsets.UTF_8)
	            + "&image_type=photo"
	            + "&per_page=3";

	    PixabayResponseDTO respuesta = ExternalHTTPRequestHandler
	            .doGetAndConvertToDto(url, PixabayResponseDTO.class);

	    if (respuesta == null || respuesta.getHits() == null || respuesta.getHits().isEmpty()) {
	        throw new ImagenNoEncontradaException("No se encontró ninguna imagen para el término: " + termino);
	    }

	    String imageUrl = respuesta.getHits().get(0).getWebformatURL();

	    try {
	        // Descarga los bytes de la imagen directamente desde la URL de Pixabay
	        return URI.create(imageUrl).toURL().openStream().readAllBytes();
	    } catch (IOException e) {
	        throw new IllegalStateException("Error al descargar la imagen desde Pixabay.", e);
	    }
	}
}
