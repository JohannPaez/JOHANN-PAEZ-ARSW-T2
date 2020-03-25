package edu.eci.coronavirus.cache;


import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import edu.eci.coronavirus.model.ContenidoJson;



@Component
public class CoronavirusCache {
	
	// String (Conuntry) ContenidoJson (Tiempo para verificar el cache, JSON)
	private ConcurrentHashMap<String, ContenidoJson> cacheByName = new ConcurrentHashMap<>();
	// Cache en general
	private long time;
	
	
	public synchronized boolean isThereCache(String name) {
		//System.out.println("\n -----------------------------------ENTRO A MIRAR SI HAY CACHE----------------------------------------------- \n");
		boolean isThereCache = false;
		if (cacheByName.get(name) != null && System.currentTimeMillis() - cacheByName.get(name).getTiempo() <= 1000 * 60 * 5) {
			isThereCache = true;
		}	
		return isThereCache;
	}
	
	public boolean isThereAllCache() {
		boolean isThereCache = false;
		if (time != 0 && (System.currentTimeMillis() - time) <= 1000 * 60 * 5) {
			isThereCache = true;
		}	
		return isThereCache;
	}
	
	public void saveCache(String name, String json) {
		ContenidoJson contenidoJson = new ContenidoJson(System.currentTimeMillis(), json);
		cacheByName.put(name, contenidoJson);
		System.out.println("GUARDO EN CACHE!");
	}
	
	public String getCache(String name) {
		return cacheByName.get(name).getJson();
	}
	
	public ConcurrentHashMap<String, ContenidoJson> getAllCache() {
		return cacheByName;
	}
	
	public void timeCache() {
		time = System.currentTimeMillis();
	}
}
