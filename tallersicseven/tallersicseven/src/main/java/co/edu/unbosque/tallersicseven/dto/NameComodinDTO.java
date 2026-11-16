package co.edu.unbosque.tallersicseven.dto;

public class NameComodinDTO {
	private String name;
	private String url;
	public NameComodinDTO() {
		super();
	}
	public NameComodinDTO(String name, String url) {
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
		return "NameComodinDTO [name=" + name + ", url=" + url + "]";
	}
	
	
	
}
