package com.example.demo.controller;

import java.io.IOException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CaseApplicationController {
	@GetMapping("/api/v1/{citycode1}/{citycode2}")
	public void Bahnhof(@PathVariable String citycode1, @PathVariable String citycode2) throws IOException {
		String []cities = {citycode1, citycode2};
		new CaseApplicationCountDistance(cities);
	}	
}	

	


