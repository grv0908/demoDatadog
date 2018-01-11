package com.example.demoDatadog;


import org.honton.chas.datadog.apm.TraceOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demoDatadog.filter.CorrelatingRestClient;

@SpringBootApplication
@RestController
@TraceOperation(type=TraceOperation.WEB)
public class Service4 {
	
	public void someLongTask() {
		try {
			Thread.sleep(60);
		} catch (Exception e) {

		}
	}

	@RequestMapping("/api4")
	public String printDate() {
		someLongTask();
		return restTemplate().getForString("http://localhost:9005/api5");
	}

	@Bean
	CorrelatingRestClient restTemplate() {
		return new CorrelatingRestClient();
	}
	public static void main(String[] args) {
		SpringApplication.run(Service4.class, "--spring.application.name=service4", "--server.port=9004");
	}
}
