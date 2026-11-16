package co.edu.unbosque.tallersicseven.dto;

public class PokemonResumenDTO {
	private String name;

	public PokemonResumenDTO() {
		super();
	}

	public PokemonResumenDTO(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "PokemonResumenDTO [name=" + name + "]";
	}

}
