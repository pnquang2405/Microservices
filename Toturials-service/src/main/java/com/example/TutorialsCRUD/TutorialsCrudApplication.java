package com.example.TutorialsCRUD;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class TutorialsCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(TutorialsCrudApplication.class, args);
	}

}
