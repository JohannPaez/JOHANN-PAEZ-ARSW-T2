package edu.eci.coronavirus.thread;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import edu.eci.coronavirus.cache.CoronavirusCache;
import edu.eci.coronavirus.model.ContenidoJson;

public class CoronaVirusThread extends Thread {
	
	@Autowired
	CoronavirusCache serviceCache;
	
	
	
	
	
	
	@Override
	public void run() {
		
	}
	
	private ConcurrentHashMap<String, ContenidoJson> saveAllCache(String cases) {
		String json = cases;
		JSONObject jo = new JSONObject(json);
		JSONObject data = jo.getJSONObject("data");
		JSONArray covid19Stats = jo.getJSONObject("data").getJSONArray("covid19Stats");
		
		JSONArray stats = new JSONArray();
		HashMap<String, String> res = new HashMap();
		System.out.println("\n todo bien ---------------------------------------------------------------- \n");
		for (int i = 0; i < covid19Stats.length(); i++) {
			stats = new JSONArray();
			res = new HashMap();
			int deaths = 0;
			int infected = 0;
			int cured = 0;
			JSONObject ciclo = new JSONObject(covid19Stats.get(i).toString());		
			deaths += (int) ciclo.get("deaths");
			infected += (int) ciclo.get("confirmed");
			cured += (int) ciclo.get("recovered");	
			System.out.println("\n Bien ---------------------------------------------------------------- \n");
			
			if (serviceCache.isThereCache(ciclo.get("country").toString())) {
				String jsonCountry = serviceCache.getCache(ciclo.get("country").toString());
				System.out.println("\n CADENA NORMAL ---------------------------------------------------------------- \n" + ciclo.get("country").toString() + jsonCountry);
				
				System.out.println("\n Entro consumir cache cache ---------------------------------------------------------------- \n");		
				//String jsonCountry = serviceCache.getCache(ciclo.get("country").toString());
				System.out.println("\n CADENA NORMAL ---------------------------------------------------------------- \n" + jsonCountry);
				//String reemplazado = jsonCountry.replace("\"", "\\\"");
				String reemplazado= jsonCountry.replace("[", "");
				reemplazado= reemplazado.replace("]", "");
				String resp = "{\"Num Deaths\":\"0\",\"Num Cured\":\"0\",\"Num Infected\":\"1\"}";
				String resp2 = new String(reemplazado);
				String otro = "{\"city\":\"chicago\",\"name\":\"jon doe\",\"age\":\"22\"}";
				JSONObject jaja = new JSONObject(otro);
				System.out.println("\n REEMPLAZADO ---------------------------------------------- \n" + reemplazado + " \n" + otro);
				JSONObject jsonObjectCountry = new JSONObject(reemplazado);
				System.out.println("\n ANTIGUOS" + jsonObjectCountry.get("infected") + "\n NUEVOS" + infected + "\n");
				System.out.println("\n BIEN?????????????????---------------------------------------------- \n" + jsonObjectCountry + "\n" + reemplazado);
				jsonObjectCountry.put("deaths", String.valueOf(Integer.parseInt((String) jsonObjectCountry.get("deaths")) + deaths));
				System.out.println("\n PASO1---------------------------------------------- \n" + jsonObjectCountry + "\n" + reemplazado);
				jsonObjectCountry.put("infected", String.valueOf(Integer.parseInt((String) jsonObjectCountry.get("infected")) + infected));
				jsonObjectCountry.put("cured",  String.valueOf(Integer.parseInt((String) jsonObjectCountry.get("cured")) + cured));
				
				serviceCache.saveCache(ciclo.get("country").toString(), jsonObjectCountry.toString());
				
				System.out.println("\n LLEGO FINALLL ---------------------------------------------- \n" + jsonObjectCountry + "\n" + 
				reemplazado + "\n ANTIGUOS" + jsonObjectCountry.get("infected") + "\n NUEVOS" + infected + "\n");
			} else {
				res.put("deaths", String.valueOf(deaths));
				res.put("infected", String.valueOf(infected));
				res.put("cured", String.valueOf(cured));
				stats.put(res);
				serviceCache.saveCache(ciclo.get("country").toString(), stats.toString());
				System.out.println("\n Entro guardar cache cache ---------------------------------------------------------------- \n");
				String jsonCountry = serviceCache.getCache(ciclo.get("country").toString());
				System.out.println("\n CADENA NORMAL SEGUNDA PARTE CICLO  ---------------------------------------------------------------- \n" + ciclo.get("country").toString() + jsonCountry);
			}			
		}

		return serviceCache.getAllCache();
	}
}
