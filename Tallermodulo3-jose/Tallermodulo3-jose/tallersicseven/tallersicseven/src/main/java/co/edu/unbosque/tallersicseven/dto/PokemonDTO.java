package co.edu.unbosque.tallersicseven.dto;

import java.util.List;

public class PokemonDTO {
	
	private int id;
	private String name;
	private int base_experience;
	private int height;
	private int weight;
	private List<PokemonTipoDTO> types;
	private List<PokemonStatDTO> stats;
	private PokemonSpriteDTO sprites;
	public PokemonDTO() {
		super();
	}
	public PokemonDTO(int id, String name, int base_experience, int height, int weight, List<PokemonTipoDTO> types,
			List<PokemonStatDTO> stats, PokemonSpriteDTO sprites) {
		super();
		this.id = id;
		this.name = name;
		this.base_experience = base_experience;
		this.height = height;
		this.weight = weight;
		this.types = types;
		this.stats = stats;
		this.sprites = sprites;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getBase_experience() {
		return base_experience;
	}
	public void setBase_experience(int base_experience) {
		this.base_experience = base_experience;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public List<PokemonTipoDTO> getTypes() {
		return types;
	}
	public void setTypes(List<PokemonTipoDTO> types) {
		this.types = types;
	}
	public List<PokemonStatDTO> getStats() {
		return stats;
	}
	public void setStats(List<PokemonStatDTO> stats) {
		this.stats = stats;
	}
	public PokemonSpriteDTO getSprites() {
		return sprites;
	}
	public void setSprites(PokemonSpriteDTO sprites) {
		this.sprites = sprites;
	}
	@Override
	public String toString() {
		return "PokemonDTO [id=" + id + ", name=" + name + ", base_experience=" + base_experience + ", height=" + height
				+ ", weight=" + weight + ", types=" + types + ", stats=" + stats + ", sprites=" + sprites + "]";
	}
	
	

}
