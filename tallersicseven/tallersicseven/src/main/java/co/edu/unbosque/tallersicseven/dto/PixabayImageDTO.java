
package co.edu.unbosque.tallersicseven.dto;

import java.util.Objects;

public class PixabayImageDTO {

	private int id;
	private String tags;
	private String previewURL;
	private String webformatURL;

	public PixabayImageDTO() {

	}

	public PixabayImageDTO(int id, String tags, String previewURL, String webformatURL) {
		this.id = id;
		this.tags = tags;
		this.previewURL = previewURL;
		this.webformatURL = webformatURL;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getPreviewURL() {
		return previewURL;
	}

	public void setPreviewURL(String previewURL) {
		this.previewURL = previewURL;
	}

	public String getWebformatURL() {
		return webformatURL;
	}

	public void setWebformatURL(String webformatURL) {
		this.webformatURL = webformatURL;
	}

	@Override
	public String toString() {
		return "PixabayImageDTO [id=" + id + ", tags=" + tags + ", previewURL=" + previewURL + ", webformatURL="
				+ webformatURL + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, tags, previewURL, webformatURL);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		PixabayImageDTO other = (PixabayImageDTO) obj;

		return id == other.id && Objects.equals(tags, other.tags) && Objects.equals(previewURL, other.previewURL)
				&& Objects.equals(webformatURL, other.webformatURL);
	}
}
