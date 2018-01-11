package com.example.demoDatadog;


import java.util.Date;

import org.honton.chas.datadog.apm.TraceOperation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demoDatadog.filter.CorrelatingRestClient;

@SpringBootApplication
@RestController
@TraceOperation(type=TraceOperation.WEB)
public class Service5 {

	@Bean
	CorrelatingRestClient restTemplate() {
		return new CorrelatingRestClient();
	}

	@RequestMapping("/api5")
	public String printDate() {
		return "Service 5 called";
	}

	public static void main(String[] args) {
		SpringApplication.run(Service5.class, "--spring.application.name=service5", "--server.port=9005");
	}
}
