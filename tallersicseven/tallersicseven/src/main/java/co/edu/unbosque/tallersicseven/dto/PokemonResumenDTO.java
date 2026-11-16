package co.edu.unbosque.tallersicseven.dto;

public class PokemonResumenDTO {
	private String name;
	private String url;
	public PokemonResumenDTO() {
		super();
	}
	public PokemonResumenDTO(String name, String url) {
		super();
		this.name = name;
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "PokemonResumenDTO [name=" + name + ", url=" + url + "]";
	}
	
	
	
	

}
