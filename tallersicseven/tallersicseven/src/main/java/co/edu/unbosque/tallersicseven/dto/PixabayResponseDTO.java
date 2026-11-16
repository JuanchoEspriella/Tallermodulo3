
package co.edu.unbosque.tallersicseven.dto;

import java.util.List;
import java.util.Objects;

public class PixabayResponseDTO {

	private int total;
	private int totalHits;
	private List<PixabayImageDTO> hits;

	public PixabayResponseDTO() {

	}

	public PixabayResponseDTO(int total, int totalHits, List<PixabayImageDTO> hits) {
		this.total = total;
		this.totalHits = totalHits;
		this.hits = hits;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getTotalHits() {
		return totalHits;
	}

	public void setTotalHits(int totalHits) {
		this.totalHits = totalHits;
	}

	public List<PixabayImageDTO> getHits() {
		return hits;
	}

	public void setHits(List<PixabayImageDTO> hits) {
		this.hits = hits;
	}

	@Override
	public String toString() {
		return "PixabayResponseDTO [total=" + total + ", totalHits=" + totalHits + ", hits=" + hits + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(total, totalHits, hits);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		PixabayResponseDTO other = (PixabayResponseDTO) obj;

		return total == other.total && totalHits == other.totalHits && Objects.equals(hits, other.hits);
	}
}
