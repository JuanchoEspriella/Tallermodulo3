package co.edu.unbosque.tallersicseven.dto;

public class PokemonStatDTO {
	
	private int base_stat;
	private int effort;
	private NameComodinDTO stat;
	
	public PokemonStatDTO() {
		// TODO Auto-generated constructor stub
	}

	public PokemonStatDTO(int base_stat, int effort, NameComodinDTO stat) {
		super();
		this.base_stat = base_stat;
		this.effort = effort;
		this.stat = stat;
	}

	public int getBase_stat() {
		return base_stat;
	}

	public void setBase_stat(int bases_stat) {
		this.base_stat = bases_stat;
	}

	public int getEffort() {
		return effort;
	}

	public void setEffort(int effort) {
		this.effort = effort;
	}

	public NameComodinDTO getStat() {
		return stat;
	}

	public void setStat(NameComodinDTO stat) {
		this.stat = stat;
	}

	@Override
	public String toString() {
		return "PokemonStatDTO [bases_stat=" + base_stat + ", effort=" + effort + ", stat=" + stat + "]";
	}
	
	

}
