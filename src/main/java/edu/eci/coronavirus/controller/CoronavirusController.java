package edu.eci.coronavirus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.coronavirus.services.CoronavirusServices;

@RestController
@RequestMapping(value = "/cases")
public class CoronavirusController {
	
	@Autowired
	CoronavirusServices services;
	
	
	/**
	 * Da los casos de coronavirus de un pais
	 * @param country Es el país 
	 * @return Respuesta http, diciendo si la petición fue correcta o no
	 */
	@RequestMapping(value = "/{country}", method=RequestMethod.GET)
	public ResponseEntity<?> getCasesByCountry(@PathVariable String country){
		return new ResponseEntity<>(services.getCasesByCountry(country),HttpStatus.ACCEPTED);
	}
	
	
	/**
	 * Da las propiedades de los casos de coronavirus de un pais
	 * @param country Es el país 
	 * @return Respuesta http, diciendo si la petición fue correcta o no
	 */
	@RequestMapping(value = "/{country}/{index}", method=RequestMethod.GET)
	public ResponseEntity<?> getPropiedades(@PathVariable String country, @PathVariable String index){
		System.out.println("ENTRA PROPIEDADES");
		return new ResponseEntity<>(services.propiedades(country),HttpStatus.ACCEPTED);
	}
	
	
	/**
	 * Da todos los casos de coronavirus en el mundo
	 * @return Respuesta http, diciendo si la petición fue correcta o no
	 */
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<?> getAllCases(){
		try {
			return new ResponseEntity<>(services.getAllCases(),HttpStatus.ACCEPTED);
		} catch (InterruptedException e) {
			return new ResponseEntity<>(false,HttpStatus.NOT_FOUND);
		}
	}

}
