package edu.eci.coronavirus.services;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.eci.coronavirus.cache.CoronavirusCache;
import edu.eci.coronavirus.http.HttpConnectionService;
import edu.eci.coronavirus.model.ContenidoJson;



@Service
public class CoronavirusServices {
	
	@Autowired
	HttpConnectionService serviceHttp;
	@Autowired
	CoronavirusCache serviceCache;
	
	public String getCasesByCountry(String country) {
		String cases = null;
		
		if (serviceCache.isThereCache(country)) {
			cases = serviceCache.getCache(country);
			System.out.println("Consumio CACHE!");
		} else {
			cases = serviceHttp.getCasesByCountry(country);
			serviceCache.saveCache(country, cases=getStatsByCountry(cases));
		}
				
		return cases;
	}
	
	public String getAllCases() {
		ConcurrentHashMap<String, ContenidoJson> casesMap = new ConcurrentHashMap<>();
		String cases = null;
		/*
		if (serviceCache.isThereAllCache()) {
			casesMap = serviceCache.getAllCache();
			System.out.println("Consumio CACHE!");
		} else {
			cases = serviceHttp.getAllCases();
			casesMap = saveAllCache(cases);			
		}
		JSONObject stats = new JSONObject(casesMap);
		cases = stats.toString();*/
		cases = serviceHttp.getAllCases();
		
		return cases;
	}
	
	private String getStatsByCountry(String jsonCountry) {
		String json = jsonCountry;
		JSONObject jo = new JSONObject(json);
		JSONObject data = jo.getJSONObject("data");
		JSONArray covid19Stats = jo.getJSONObject("data").getJSONArray("covid19Stats");		

		JSONArray stats = new JSONArray();
		HashMap<String, String> res = new HashMap();
		int deaths = 0;
		int infected = 0;
		int cured = 0;
		for (int i = 0; i < covid19Stats.length(); i++) {
			JSONObject ciclo = new JSONObject(covid19Stats.get(i).toString());		
			deaths += (int) ciclo.get("deaths");
			infected += (int) ciclo.get("confirmed");
			cured += (int) ciclo.get("recovered");			
		}		
		res.put("deaths", String.valueOf(deaths));
		res.put("infected", String.valueOf(infected));
		res.put("cured", String.valueOf(cured));	
		stats.put(res);
		return stats.toString();
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
				System.out.println("\n Entro consumir cache cache ---------------------------------------------------------------- \n");		
				String jsonCountry = serviceCache.getCache(ciclo.get("country").toString());
				String remplazado = jsonCountry.replace("\"", "   \'\'\" ");
				remplazado= jsonCountry.replace("[", "\"");
				remplazado= remplazado.replace("]", "\"");
				JSONObject jsonObjectCountry = new JSONObject(remplazado);
				System.out.println("\n BIEN?????????????????---------------------------------------------- \n" + jsonObjectCountry);
				jsonObjectCountry.put("Num Deaths", String.valueOf((int) jsonObjectCountry.get("Num Deaths") + deaths));
				jsonObjectCountry.put("Num Infected", String.valueOf((int) jsonObjectCountry.get("Num Infected") + infected));
				jsonObjectCountry.put("Num Cured",  String.valueOf((int) jsonObjectCountry.get("Num Cured") + cured));
				
				serviceCache.saveCache(ciclo.get("country").toString(), jsonObjectCountry.toString());
				
				System.out.println(ciclo.get("country").toString());
			} else {
				res.put("Num Deaths", String.valueOf(deaths));
				res.put("Num Infected", String.valueOf(infected));
				res.put("Num Cured", String.valueOf(cured));
				stats.put(res);
				serviceCache.saveCache(ciclo.get("country").toString(), stats.toString());
				System.out.println("\n Entro guardar cache cache ---------------------------------------------------------------- \n");
			}			
		}

		return serviceCache.getAllCache();
	}
}
