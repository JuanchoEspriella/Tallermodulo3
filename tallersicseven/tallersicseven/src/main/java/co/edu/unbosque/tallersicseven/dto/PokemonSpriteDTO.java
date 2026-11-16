package co.edu.unbosque.tallersicseven.dto;

public class PokemonSpriteDTO {
	
	private String front_default;
	private String back_default;
	private String front_shiny;
	private String back_shiny;
	public PokemonSpriteDTO() {
		super();
	}
	public PokemonSpriteDTO(String front_default, String back_default, String front_shiny, String back_shiny) {
		super();
		this.front_default = front_default;
		this.back_default = back_default;
		this.front_shiny = front_shiny;
		this.back_shiny = back_shiny;
	}
	public String getFront_default() {
		return front_default;
	}
	public void setFront_default(String front_default) {
		this.front_default = front_default;
	}
	public String getBack_default() {
		return back_default;
	}
	public void setBack_default(String back_default) {
		this.back_default = back_default;
	}
	public String getFront_shiny() {
		return front_shiny;
	}
	public void setFront_shiny(String front_shiny) {
		this.front_shiny = front_shiny;
	}
	public String getBack_shiny() {
		return back_shiny;
	}
	public void setBack_shiny(String back_shiny) {
		this.back_shiny = back_shiny;
	}
	@Override
	public String toString() {
		return "PokemonSpriteDTO [front_default=" + front_default + ", back_default=" + back_default + ", front_shiny="
				+ front_shiny + ", back_shiny=" + back_shiny + "]";
	}
	
	
	
}
