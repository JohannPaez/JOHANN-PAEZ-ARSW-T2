package edu.eci.coronavirus.model;

public class ContenidoJson {
	
	
	private long tiempo;
	private String Json;
	
	/**
	 * Crea un ContenidoJson que guarda el tiempo en el que fue consultado y el json dado
	 * @param tiempo Es el tiempo dado
	 * @param Json Es el JSON en formato string dado
	 */
	public ContenidoJson(long tiempo, String Json) {
		this.tiempo = tiempo;
		this.Json = Json;
	}
	
	/**
	 * Retorna el tiempo
	 * @return tiempo
	 */
	public long getTiempo() {
		return tiempo;
	}

	/**
	 * Cambia el tiempo
	 * @param tiempo
	 */
	public void setTiempo(long tiempo) {
		this.tiempo = tiempo;
	}

	/**
	 * Da el contenido del json
	 * @return json
	 */
	public String getJson() {
		return Json;
	}
	
	/**
	 * Cambie el contenido del json 
	 * @param json
	 */
	public void setJson(String json) {
		Json = json;
	}

}
