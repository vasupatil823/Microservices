package com.vasu.microservices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vasu.microservices.bean.LimitConfiguration;
import com.vasu.microservices.propertiesconfiguration.PropertiesConfiguration;

@RestController
public class LimitsController {
	
	@Autowired
	PropertiesConfiguration configuration;
	
	@GetMapping("/limits")
	public LimitConfiguration retriveLimitConfigurations() {
		return new LimitConfiguration(configuration.getMaximum(), configuration.getMinimum());
	}

}
