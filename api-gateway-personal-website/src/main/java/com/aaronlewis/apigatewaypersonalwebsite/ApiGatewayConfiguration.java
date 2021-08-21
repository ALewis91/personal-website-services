package com.aaronlewis.apigatewaypersonalwebsite;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;

@Configuration
public class ApiGatewayConfiguration {
	
	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p -> p.path("/health")
						.uri("http://localhost:443/health"))
				.route(p -> p.path("/**")
						.filters(f -> f
								.setRequestHeader("Access-Control-Allow-Origin", ""))
						.uri("https://k8s.aaronlewis.dev:443/**"))
				.build();
	}

}
