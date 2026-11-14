package co.edu.unbosque.tallersicseven.dto;

import java.util.List;

public class PokemonListaDTO {

	private int count;
	private String next;
	private String previous;
	private List<PokemonResumenDTO> results;
	public PokemonListaDTO() {
		super();
	}
	public PokemonListaDTO(int count, String next, String previous, List<PokemonResumenDTO> results) {
		super();
		this.count = count;
		this.next = next;
		this.previous = previous;
		this.results = results;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getNext() {
		return next;
	}
	public void setNext(String next) {
		this.next = next;
	}
	public String getPrevious() {
		return previous;
	}
	public void setPrevious(String previous) {
		this.previous = previous;
	}
	public List<PokemonResumenDTO> getResults() {
		return results;
	}
	public void setResults(List<PokemonResumenDTO> results) {
		this.results = results;
	}
	@Override
	public String toString() {
		return "PokemonListaDTO [count=" + count + ", next=" + next + ", previous=" + previous + ", results=" + results
				+ "]";
	}
	
	
}
