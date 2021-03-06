package edu.eci.coronavirus.http;

import org.springframework.stereotype.Component;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;


@Component
public class HttpConnectionService {
	
	/**
	 * Da los casos de coronavirus de un pais
	 * @param country Es el pa�s 
	 * @return Con el contenido deseado
	 */
	public String getCasesByCountry(String country) {
		System.out.println("ENTRA " + country);
		String Json = null;
		try {
			HttpResponse<String> response = Unirest.get("https://covid-19-coronavirus-statistics.p.rapidapi.com/v1/stats?country=" + country)
					.header("x-rapidapi-host", "covid-19-coronavirus-statistics.p.rapidapi.com")
					.header("x-rapidapi-key", "54276a88a3msh34d1a569336cf16p1e0818jsn40847117ffe2")
					.asString();
			Json = response.getBody();
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Json;
	}
	
	
	/**
	 * Da los casos de coronavirus de todo el mundo
	 * @return Con el contenido deseado
	 */
	public String getAllCases() {
		String Json = null;
		try {
			HttpResponse<String> response = Unirest.get("https://covid-19-coronavirus-statistics.p.rapidapi.com/v1/stats")
					.header("x-rapidapi-host", "covid-19-coronavirus-statistics.p.rapidapi.com")
					.header("x-rapidapi-key", "54276a88a3msh34d1a569336cf16p1e0818jsn40847117ffe2")
					.asString();
			Json = response.getBody();
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return Json;
		
	}
	
	/**
	 * Da las propiedades de los casos de coronavirus de un pais
	 * @param country Es el pa�s 
	 * @return Con el contenido deseado
	 */
	public String propiedades(String country) {
		String Json = null;
		try {
			HttpResponse<String> response = Unirest.get("https://restcountries-v1.p.rapidapi.com/name/" + country)
					.header("x-rapidapi-host", "restcountries-v1.p.rapidapi.com")
					.header("x-rapidapi-key", "54276a88a3msh34d1a569336cf16p1e0818jsn40847117ffe2")
					.asString();
			Json = response.getBody();
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Json;
	}
	
}
