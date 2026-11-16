package co.edu.unbosque.tallersicseven.exception;

public class ExternalApiException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ExternalApiException(String mensaje) {
		super(mensaje);
	}

	public ExternalApiException(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}
}
