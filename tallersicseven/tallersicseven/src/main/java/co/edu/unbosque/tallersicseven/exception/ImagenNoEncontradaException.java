package co.edu.unbosque.tallersicseven.exception;

public class ImagenNoEncontradaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ImagenNoEncontradaException(String mensaje) {
		super(mensaje);
	}
}
