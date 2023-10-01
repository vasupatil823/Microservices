package com.vasu.gateway.springcloudapigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomRoutesAPIGatewayConfiguration {

	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
		//we can write multiple routes
		return builder.routes()
				.route(p -> p.path("/get")
				.filters(f->f.addRequestHeader("myHeader", "myHeaderValue").
						addRequestParameter("Param", "ParamValue"))
				.uri("http://httpbin.org:80"))
				.route(p -> p.path("/currency-exchange/**").
						uri("lb://currency-exchange-service"))
				.route(p -> p.path("/currency-conversion-loadbalancing-with-eureka-naming-server/**").
						uri("lb://currency-conversion-service"))
				.route(p -> p.path("/currency-conversion-eureka/**").
						filters(f->f.rewritePath(
								"currency-conversion-eureka/(?<segment>.*)", 
								"currency-conversion-loadbalancing-with-eureka-naming-server/${segment}")).
						uri("lb://currency-conversion-service"))
				.build();
	}
}
