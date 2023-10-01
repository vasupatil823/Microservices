package com.vasu.currencyconversionservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.vasu.currencyconversionservice.bean.CurrencyConversionBean;
import com.vasu.currencyconversionservice.proxy.CurrencyExchangeServiceProxy;

@RestController
public class CurrencyConversionController {
	
	@Autowired
	private CurrencyExchangeServiceProxy proxy;
	
	@Autowired
	private WebClient loadBalancingClient;
	
	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrency(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {
		// The below code is a manual stuff there is a solution called feign using which
		// we can do this in very few and easy steps. we have done in new api below
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity(
				"http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversionBean.class,
				uriVariables);
		CurrencyConversionBean response = responseEntity.getBody();
		return new CurrencyConversionBean(response.getId(), from, to, response.getConversionMultiple(), quantity,
				quantity.multiply(response.getConversionMultiple()), response.getPort());
	}
	
	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {
		//Solution of above problem using feign
		CurrencyConversionBean response = proxy.retriveExchangeValue(from, to);
		return new CurrencyConversionBean(response.getId(), from, to, response.getConversionMultiple(), quantity,
				quantity.multiply(response.getConversionMultiple()), response.getPort());
	}
	
	@GetMapping("/currency-conversion-loadbalancing/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrencySpringCloudLoadBalancer(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {
		//Solution of above problem using feign
		CurrencyConversionBean response = loadBalancingClient.get().uri("http://currency-exchange-service/currency-exchange/from/"+from+"/to/"+to+"").retrieve().toEntity(CurrencyConversionBean.class).block().getBody();
		return new CurrencyConversionBean(response.getId(), from, to, response.getConversionMultiple(), quantity,
				quantity.multiply(response.getConversionMultiple()), response.getPort());
	}
	
	@GetMapping("/currency-conversion-loadbalancing-with-eureka-naming-server/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrencySpringCloudLoadBalancerWithEurekaNamingServer(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {
		//Solution of above problem using feign
		CurrencyConversionBean response = restTemplate.getForObject("http://currency-exchange-service/currency-exchange/from/"+from+"/to/"+to+"", CurrencyConversionBean.class);
		return new CurrencyConversionBean(response.getId(), from, to, response.getConversionMultiple(), quantity,
				quantity.multiply(response.getConversionMultiple()), response.getPort());
	}
}
