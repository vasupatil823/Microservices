package com.vasu.currencyconversionservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.vasu.currencyconversionservice.loadbalancer.InstanceSupplier;

@Configuration
public class ServerInstanceConfiguration {

	@Bean
    ServiceInstanceListSupplier serviceInstanceListSupplier() {
        return new InstanceSupplier("currency-exchange-service");
    }
	
	@Bean
    WebClient webClient(WebClient.Builder builder) {
        return builder.build();
    }
	
	@LoadBalanced
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
