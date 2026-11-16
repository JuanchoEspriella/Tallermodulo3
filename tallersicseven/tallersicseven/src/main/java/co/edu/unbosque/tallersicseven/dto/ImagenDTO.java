
package co.edu.unbosque.tallersicseven.dto;

import java.util.Objects;

public class ImagenDTO {

	private int pixabayId;
	private String nombreArchivo;
	private String tags;
	private String urlDescarga;

	public ImagenDTO() {

	}

	public ImagenDTO(int pixabayId, String nombreArchivo, String tags, String urlDescarga) {
		this.pixabayId = pixabayId;
		this.nombreArchivo = nombreArchivo;
		this.tags = tags;
		this.urlDescarga = urlDescarga;
	}

	public int getPixabayId() {
		return pixabayId;
	}

	public void setPixabayId(int pixabayId) {
		this.pixabayId = pixabayId;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getUrlDescarga() {
		return urlDescarga;
	}

	public void setUrlDescarga(String urlDescarga) {
		this.urlDescarga = urlDescarga;
	}

	@Override
	public String toString() {
		return "ImagenDTO [pixabayId=" + pixabayId + ", nombreArchivo=" + nombreArchivo + ", tags=" + tags
				+ ", urlDescarga=" + urlDescarga + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(pixabayId, nombreArchivo, tags, urlDescarga);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		ImagenDTO other = (ImagenDTO) obj;

		return pixabayId == other.pixabayId && Objects.equals(nombreArchivo, other.nombreArchivo)
				&& Objects.equals(tags, other.tags) && Objects.equals(urlDescarga, other.urlDescarga);
	}
}
