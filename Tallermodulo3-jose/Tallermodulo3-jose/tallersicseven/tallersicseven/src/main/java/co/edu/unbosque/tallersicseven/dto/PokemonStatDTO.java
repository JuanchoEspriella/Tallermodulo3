package co.edu.unbosque.tallersicseven.dto;

public class PokemonStatDTO {
	
	private int bases_stat;
	private int effort;
	private NameComodinDTO stat;
	
	public PokemonStatDTO() {
		// TODO Auto-generated constructor stub
	}

	public PokemonStatDTO(int bases_stat, int effort, NameComodinDTO stat) {
		super();
		this.bases_stat = bases_stat;
		this.effort = effort;
		this.stat = stat;
	}

	public int getBases_stat() {
		return bases_stat;
	}

	public void setBases_stat(int bases_stat) {
		this.bases_stat = bases_stat;
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
		return "PokemonStatDTO [bases_stat=" + bases_stat + ", effort=" + effort + ", stat=" + stat + "]";
	}
	
	

}
