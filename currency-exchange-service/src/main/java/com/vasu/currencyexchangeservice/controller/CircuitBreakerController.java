package com.vasu.currencyexchangeservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;

@RestController
public class CircuitBreakerController {
	
	private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

	@GetMapping("sample-api")
	//@Retry(name = "sample-api", fallbackMethod = "fallbackresponse")   //by default it will retry for 3 times, to make the custom we need to add the propery in properties file
	//@CircuitBreaker(name = "default",fallbackMethod = "fallbackresponse")
	//@RateLimiter(name = "default")
	@Bulkhead(name = "default")
	public String sampleApi() {
		logger.info("Sample api call received");
//		ResponseEntity<String> forEntity = new RestTemplate().getForEntity("localhost:8080/dummy-api", String.class);
//		return forEntity.getBody();
		return "sample-api";
	}
	
	public String fallbackresponse(Exception ex) {
		return "fallback-response";
	}
}
