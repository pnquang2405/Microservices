package com.test.gatewayservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableEurekaClient
@RefreshScope
public class GatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
	}
	
	@Autowired
	private Environment environment;
	
	@EventListener({
        ApplicationReadyEvent.class,
        RefreshScopeRefreshedEvent.class
    })
    public void onEvent() {
		Logger log = LoggerFactory.getLogger(this.getClass());
        log.info("New secret token:" + environment.getProperty("token.secret"));
    }

}
