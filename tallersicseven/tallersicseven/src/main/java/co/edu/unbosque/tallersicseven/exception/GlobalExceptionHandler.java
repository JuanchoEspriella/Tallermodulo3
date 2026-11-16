package co.edu.unbosque.tallersicseven.exception;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(ImagenNoEncontradaException.class)
	public ResponseEntity<ErrorResponse> manejarImagenNoEncontrada(ImagenNoEncontradaException exception) {
		return crearRespuesta(HttpStatus.NOT_FOUND, exception.getMessage());
	}

	@ExceptionHandler(ExternalApiException.class)
	public ResponseEntity<ErrorResponse> manejarErrorApi(ExternalApiException exception) {
		return crearRespuesta(HttpStatus.BAD_GATEWAY, exception.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> manejarErrorInterno(Exception exception) {
		log.error("Error interno procesando la solicitud", exception);
		return crearRespuesta(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error interno en el servidor.");
	}

	private ResponseEntity<ErrorResponse> crearRespuesta(HttpStatus estado, String mensaje) {
		ErrorResponse respuesta = new ErrorResponse(estado.value(), mensaje, LocalDateTime.now());
		return ResponseEntity.status(estado).body(respuesta);
	}

	public static class ErrorResponse {

		private int codigo;
		private String mensaje;
		private LocalDateTime fecha;

		public ErrorResponse() {
		}

		public ErrorResponse(int codigo, String mensaje, LocalDateTime fecha) {
			this.codigo = codigo;
			this.mensaje = mensaje;
			this.fecha = fecha;
		}

		public int getCodigo() {
			return codigo;
		}

		public void setCodigo(int codigo) {
			this.codigo = codigo;
		}

		public String getMensaje() {
			return mensaje;
		}

		public void setMensaje(String mensaje) {
			this.mensaje = mensaje;
		}

		public LocalDateTime getFecha() {
			return fecha;
		}

		public void setFecha(LocalDateTime fecha) {
			this.fecha = fecha;
		}
	}
}
