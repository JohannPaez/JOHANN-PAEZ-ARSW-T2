package edu.eci.coronavirus.model;

public class ContenidoJson {
	
	
	private long tiempo;
	private String Json;
	
	public ContenidoJson(long tiempo, String Json) {
		this.tiempo = tiempo;
		this.Json = Json;
	}
	
	public long getTiempo() {
		return tiempo;
	}

	public void setTiempo(long tiempo) {
		this.tiempo = tiempo;
	}

	public String getJson() {
		return Json;
	}

	public void setJson(String json) {
		Json = json;
	}

}
