package edu.eci.coronavirus.cache;


import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import edu.eci.coronavirus.model.ContenidoJson;



@Component
public class CoronavirusCache {
	
	// String (Conuntry) ContenidoJson (Tiempo para verificar el cache, JSON)
	private ConcurrentHashMap<String, ContenidoJson> cacheByName = new ConcurrentHashMap<>();
	private ConcurrentHashMap<String, ContenidoJson> propiedades = new ConcurrentHashMap<>();
	// Cache en general
	private long time;
	
	
	/**
	 * Dice si hay cache de un pais
	 * @param name Es el nombre del pais
	 * @return true, false, dependiendo de la condicion
	 */
	public synchronized boolean isThereCache(String name) {
		//System.out.println("\n -----------------------------------ENTRO A MIRAR SI HAY CACHE----------------------------------------------- \n");
		boolean isThereCache = false;
		if (cacheByName.get(name) != null && System.currentTimeMillis() - cacheByName.get(name).getTiempo() <= 1000 * 60 * 5) {
			isThereCache = true;
		}	
		return isThereCache;
	}
	
	/**
	 * Dice si hay cache en las propiedades del pais
	 * @param name Es el pais a revisar
	 * @return true, false dependiendo de la condición
	 */
	public synchronized boolean isThereCachePropiedades(String name) {
		boolean isThereCache = false;
		if (propiedades.get(name) != null && System.currentTimeMillis() - propiedades.get(name).getTiempo() <= 1000 * 60 * 5) {
			isThereCache = true;
		}	
		return isThereCache;
	}
	
	/**
	 * Dice si todo el cache esta disponible
	 * @return true, false dependiendo de la condición
	 */
	public boolean isThereAllCache() {
		boolean isThereCache = false;
		if (time != 0 && (System.currentTimeMillis() - time) <= 1000 * 60 * 5) {
			isThereCache = true;
		}	
		return isThereCache;
	}
	
	/**
	 * Guarda el cache de un pais
	 * @param name Es el nombre del país
	 * @param json Es el contenido a guardar
	 */
	public void saveCache(String name, String json) {
		ContenidoJson contenidoJson = new ContenidoJson(System.currentTimeMillis(), json);
		cacheByName.put(name, contenidoJson);
		System.out.println("GUARDO EN CACHE!");
	}
	
	/**
	 * Da el cache de un país
	 * @param name Es el nombre del país
	 * @return El contenido del cache
	 */
	public String getCache(String name) {
		return cacheByName.get(name).getJson();
	}
	
	/**
	 * Guarda el cache de las propiedades
	 * @param name Es el nombre del apis
	 * @param json Es el contenido a guardar
	 */
	public void saveCacheProp(String name, String json) {
		ContenidoJson contenidoJson = new ContenidoJson(System.currentTimeMillis(), json);
		propiedades.put(name, contenidoJson);
		System.out.println("GUARDO EN CACHE!");
	}
	
	/**
	 * Da el cache de las propiedades de un país
	 * @param name Es el nombre del país
	 * @return El contenido del cache
	 */
	public String getCacheProp(String name) {
		return propiedades.get(name).getJson();
	}
	
	/**
	 * Da el cache de todos los paises
	 * @return El contenido del cache
	 */
	public String getAllCache() {
		final ConcurrentHashMap<String, String> give = new ConcurrentHashMap<>();
		for (String key : cacheByName.keySet()) {
			give.put(key, getCache(key));
		}
		/*cacheByName.forEach(new BiConsumer<String, String>() {
			@Override
			public void accept(String k, String v) {
				give.put(k, getCache(k));
			}
		});*/
		
		JSONObject json = new JSONObject(give);
		return json.toString();
		//return cacheByName;
	}
	/**
	 * Reinicia el tiempo cuando se guarda todo el cache
	 */
	public void timeCache() {
		time = System.currentTimeMillis();
	}
}
