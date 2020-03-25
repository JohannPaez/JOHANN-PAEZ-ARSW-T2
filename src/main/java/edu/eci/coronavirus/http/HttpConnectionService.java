package edu.eci.coronavirus.http;

import org.springframework.stereotype.Component;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;


@Component
public class HttpConnectionService {
	
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
	
}
