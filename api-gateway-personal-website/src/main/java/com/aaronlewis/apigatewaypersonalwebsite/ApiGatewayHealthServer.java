package com.aaronlewis.apigatewaypersonalwebsite;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiGatewayHealthServer {
	
	@GetMapping("/health")
	public ResponseEntity<Object> healthCheck() {
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
}
