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

	@Operation(summary = "Buscar imágenes", description = "Busca imágenes en Pixabay y las guarda en el servidor. Devuelve una lista JSON con los datos y las rutas para visualizar o descargar cada imagen.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Resultados de búsqueda"),
			@ApiResponse(responseCode = "502", description = "No fue posible consultar o descargar desde Pixabay") })
	@GetMapping("/buscar")
	public ResponseEntity<List<ImagenDTO>> buscarImagenes(@RequestParam String termino) {
		return ResponseEntity.ok(imagenService.buscarImagenes(termino));
	}

	@Operation(summary = "Descargar imagen", description = "Devuelve como archivo la imagen almacenada en el servidor.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Archivo de imagen", content = @Content(mediaType = "application/octet-stream", schema = @Schema(type = "string", format = "binary"))),
			@ApiResponse(responseCode = "404", description = "La imagen solicitada no existe") })
	@GetMapping("/archivo/{nombreArchivo}")
	public ResponseEntity<Resource> descargarImagen(@PathVariable String nombreArchivo) {

		File archivo = imagenService.obtenerArchivo(nombreArchivo);
		Resource recurso = new FileSystemResource(archivo);

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION,
						ContentDisposition.attachment().filename(archivo.getName()).build().toString())
				.body(recurso);
	}

	@Operation(summary = "Visualizar imagen", description = "Muestra directamente una imagen almacenada en el servidor.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Imagen encontrada", content = @Content(mediaType = "image/jpeg", schema = @Schema(type = "string", format = "binary"))),
			@ApiResponse(responseCode = "404", description = "Imagen no encontrada") })
	@GetMapping(value = "/visualizar/{nombreArchivo}", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<Resource> visualizarImagen(@PathVariable String nombreArchivo) {

		File archivo = imagenService.obtenerArchivo(nombreArchivo);
		Resource recurso = new FileSystemResource(archivo);

		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).header(HttpHeaders.CONTENT_DISPOSITION,
				ContentDisposition.inline().filename(archivo.getName()).build().toString()).body(recurso);
	}

}
