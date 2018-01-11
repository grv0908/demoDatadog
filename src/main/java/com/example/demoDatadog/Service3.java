package com.example.demoDatadog;

import org.honton.chas.datadog.apm.TraceOperation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demoDatadog.filter.CorrelatingRestClient;

@SpringBootApplication
@RestController
@TraceOperation(type = TraceOperation.WEB)
public class Service3 {

	@RequestMapping("/api3")
	public String printDate() {
		return restTemplate().getForString("http://localhost:9004/api4");
	}

	@Bean
	CorrelatingRestClient restTemplate() {
		return new CorrelatingRestClient();
	}

	public static void main(String[] args) {
		SpringApplication.run(Service3.class, "--spring.application.name=service3", "--server.port=9003");
	}
}