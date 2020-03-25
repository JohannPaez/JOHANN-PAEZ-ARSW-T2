package edu.eci.coronavirus.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.eci.coronavirus.cache.CoronavirusCache;
import edu.eci.coronavirus.http.HttpConnectionService;
import edu.eci.coronavirus.model.ContenidoJson;
import edu.eci.coronavirus.thread.CoronaVirusThread;




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
	
	public String getAllCases() throws InterruptedException {
		String casesMap = null;
		String cases = null;
		
		
		if (serviceCache.isThereAllCache()) {
			cases = serviceCache.getAllCache();
			System.out.println("Consumio CACHE!");
		} else {
			cases = serviceHttp.getAllCases();
			casesMap = saveAllCache(cases);
			serviceCache.timeCache();
			cases = casesMap;
			System.out.println("Guardo CACHE!");
		}
		
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
	
	private String saveAllCache(String cases) throws InterruptedException {
		String json = cases;
		JSONObject jo = new JSONObject(json);
		JSONObject data = jo.getJSONObject("data");
		JSONArray covid19Stats = jo.getJSONObject("data").getJSONArray("covid19Stats");

		//System.out.println("FASE 1");
		int threads = 12;
		ArrayList<CoronaVirusThread> threadList = new ArrayList<>();		
		for (int j = 0; j < threads; j++) {
			CoronaVirusThread hilo = new CoronaVirusThread(this);
        	threadList.add(hilo);
		}
		
		//System.out.println("FASE 2");
		int hiloQueQuiero = 0, i = 0;		
		while (i < covid19Stats.length()) {			
			JSONObject j = (JSONObject) covid19Stats.get(i);
			threadList.get(hiloQueQuiero).addStats(j);
			hiloQueQuiero ++;
			i++;
			if (hiloQueQuiero == threadList.size()) hiloQueQuiero = 0;
		}
		
		//System.out.println("FASE 3");
		
		for (int l = 0; l < threadList.size(); l++) {
			threadList.get(l).start();
		}
		
		for (CoronaVirusThread CVT: threadList) {
			CVT.join();
		}
		
		System.out.println("TERMINO!");
		return serviceCache.getAllCache();
	}
	
	public CoronavirusCache getServicesCache() {
		return serviceCache;
	}
	
	public String propiedades(String country) {
		String cases = null;
		
		if (serviceCache.isThereCachePropiedades(country)) {
			cases = serviceCache.getCacheProp(country);
			System.out.println("Consumio CACHE!");
		} else {
			cases = serviceHttp.propiedades(country);
			String Json = serviceHttp.propiedades(country);
			serviceCache.saveCacheProp(cases, Json);
		}
				
		return cases;
	}
}
