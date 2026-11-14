package co.edu.unbosque.tallersicseven.dto;

public class PokemonTipoDTO {
	
	private int slot;
	private NameComodinDTO type;
	
	public PokemonTipoDTO() {
		// TODO Auto-generated constructor stub
	}

	public PokemonTipoDTO(int slot, NameComodinDTO type) {
		super();
		this.slot = slot;
		this.type = type;
	}

	public int getSlot() {
		return slot;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}

	public NameComodinDTO getType() {
		return type;
	}

	public void setType(NameComodinDTO type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "PokemonTipoDTO [slot=" + slot + ", type=" + type + "]";
	}
	
	

}
