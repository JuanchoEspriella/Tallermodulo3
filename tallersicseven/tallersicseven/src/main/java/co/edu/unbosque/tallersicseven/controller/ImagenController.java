package co.edu.unbosque.tallersicseven.controller;

import java.io.File;
import java.util.List;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.tallersicseven.dto.ImagenDTO;
import co.edu.unbosque.tallersicseven.service.ImagenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/imagenes")
@Tag(name = "Imágenes", description = "Búsqueda y descarga de imágenes")
@SecurityRequirement(name = "bearerAuth")
public class ImagenController {

	private final ImagenService imagenService;

	public ImagenController(ImagenService imagenService) {
		this.imagenService = imagenService;
	}

	@Operation(summary = "Buscar imágenes", description = "Busca imágenes en Pixabay y las guarda en el servidor.")
	@GetMapping("/buscar")
	public ResponseEntity<List<ImagenDTO>> buscarImagenes(@RequestParam String termino) {
		return ResponseEntity.ok(imagenService.buscarImagenes(termino));
	}

	@Operation(summary = "Descargar imagen", description = "Devuelve como archivo la imagen almacenada en el servidor.")
	@GetMapping("/archivo/{nombreArchivo}")
	public ResponseEntity<Resource> descargarImagen(@PathVariable String nombreArchivo) {
		File archivo = imagenService.obtenerArchivo(nombreArchivo);
		Resource recurso = new FileSystemResource(archivo);

		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION,
						ContentDisposition.attachment().filename(archivo.getName()).build().toString())
				.body(recurso);
	}

	@Operation(summary = "Visualizar imagen", description = "Muestra directamente una imagen almacenada en el servidor.")
	@GetMapping(value = "/visualizar/{nombreArchivo}", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<Resource> visualizarImagen(@PathVariable String nombreArchivo) {
		File archivo = imagenService.obtenerArchivo(nombreArchivo);
		Resource recurso = new FileSystemResource(archivo);

		return ResponseEntity.ok()
				.contentType(MediaType.IMAGE_JPEG)
				.header(HttpHeaders.CONTENT_DISPOSITION,
						ContentDisposition.inline().filename(archivo.getName()).build().toString())
				.body(recurso);
	}
	
	@Operation(
		summary = "Buscar y renderizar foto por palabra clave", 
		description = "Busca una imagen en Pixabay y devuelve el binario JPEG para que Swagger la renderice de una."
	)
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200", 
			description = "Imagen renderizada con éxito", 
			content = @Content(mediaType = MediaType.IMAGE_JPEG_VALUE, schema = @Schema(type = "string", format = "binary"))
		)
	})
	@GetMapping(value = "/render", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] renderizarFotoPorPalabra(
			@Parameter(description = "Palabra clave", example = "gato") 
			@RequestParam String termino) {
		// Al retornar directamente byte[] con produces = IMAGE_JPEG_VALUE, 
		// Swagger lo interpreta y dibuja la imagen de inmediato si se encuentra.
		return imagenService.obtenerBytesFotoDirecta(termino);
	}
}