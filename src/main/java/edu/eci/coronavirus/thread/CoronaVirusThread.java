package edu.eci.coronavirus.thread;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.eci.coronavirus.services.CoronavirusServices;




public class CoronaVirusThread extends Thread {
	

	private JSONArray covid19Stats;
	CoronavirusServices services;
	
	public CoronaVirusThread(CoronavirusServices services) {
		this.covid19Stats = new JSONArray();
		this.services = services;
		
	}
	
	public CoronaVirusThread(JSONArray covid19Stats) {
		this.covid19Stats = covid19Stats;
	}
	
	
	public void addStats(JSONObject json) {
		Map<String, Object> map = json.toMap();		
		covid19Stats.put(map);
	}
	public JSONArray get() {
		return covid19Stats;
	}
	
	@Override
	public void run() {	
			
		JSONArray stats = new JSONArray();
		HashMap<String, String> res = new HashMap();
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
		
			if (services.getServicesCache().isThereCache(ciclo.get("country").toString())) {
				String jsonCountry = services.getServicesCache().getCache(ciclo.get("country").toString());
				String reemplazado= jsonCountry.replace("[", "");
				reemplazado= reemplazado.replace("]", "");
				JSONObject jsonObjectCountry = new JSONObject(reemplazado);

				jsonObjectCountry.put("deaths", String.valueOf(Integer.parseInt((String) jsonObjectCountry.get("deaths")) + deaths));
				jsonObjectCountry.put("infected", String.valueOf(Integer.parseInt((String) jsonObjectCountry.get("infected")) + infected));
				jsonObjectCountry.put("cured",  String.valueOf(Integer.parseInt((String) jsonObjectCountry.get("cured")) + cured));
				
				services.getServicesCache().saveCache(ciclo.get("country").toString(), jsonObjectCountry.toString());
				
			} else {
				res.put("deaths", String.valueOf(deaths));
				res.put("infected", String.valueOf(infected));
				res.put("cured", String.valueOf(cured));
				stats.put(res);
				
				services.getServicesCache().saveCache(ciclo.get("country").toString(), stats.toString());
			}		
		}
		System.out.println("--------------------------------------------------------------------------------------"
									+ "ESTE HILO A TERMINADO SU EJECUCION ----------------------------------------------------------------");
	}

}
