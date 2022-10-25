package com.test.gatewayservice.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	@GetMapping
	public ResponseEntity<Object> test() {
		throw new AuthorException(401, "123456");
	}
}
