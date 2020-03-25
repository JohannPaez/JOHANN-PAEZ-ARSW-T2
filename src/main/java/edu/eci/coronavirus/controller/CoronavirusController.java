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
	
	@RequestMapping(value = "/{country}", method=RequestMethod.GET)
	public ResponseEntity<?> getCasesByCountry(@PathVariable String country){
		return new ResponseEntity<>(services.getCasesByCountry(country),HttpStatus.ACCEPTED);
	}
	
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<?> getAllCases(){
		try {
			return new ResponseEntity<>(services.getAllCases(),HttpStatus.ACCEPTED);
		} catch (InterruptedException e) {
			return new ResponseEntity<>(false,HttpStatus.NOT_FOUND);
		}
	}

}
