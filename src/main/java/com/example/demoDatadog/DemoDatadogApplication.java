package com.example.demoDatadog;

import java.util.HashMap;

import org.honton.chas.datadog.apm.TraceOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demoDatadog.filter.CorrelatingRestClient;

@SpringBootApplication
@RestController
@TraceOperation(type = TraceOperation.WEB)
public class DemoDatadogApplication {

	private final static Logger LOGGER = LoggerFactory.getLogger(DemoDatadogApplication.class);

	@RequestMapping("/api")
	public String printDate() {
		return restTemplate().getForString("http://localhost:9003/api3");
	}

	@Bean
	CorrelatingRestClient restTemplate() {
		return new CorrelatingRestClient();
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoDatadogApplication.class, "--spring.application.name=demoDatadogApplication",
				"--server.port=8081");
	}
}
