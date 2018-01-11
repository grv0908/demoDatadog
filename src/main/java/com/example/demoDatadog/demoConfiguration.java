package com.example.demoDatadog;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.Produces;

import org.honton.chas.datadog.apm.TraceConfiguration;
import org.honton.chas.datadog.apm.Tracer;
import org.honton.chas.datadog.apm.cdi.TracerImpl;
import org.honton.chas.datadog.apm.sender.Writer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class demoConfiguration {
	
	@Bean
    public Tracer returnTrace() {
        return new TracerImpl();
    }
	
	
	
	@Bean
    public Writer returnWriter() {
        return new Writer();
    }
	
	@Bean
	  static TraceConfiguration getDefault() {
	    return new TraceConfiguration(
	      "demoDatadog",
	      "http://localhost:8126",
	      TimeUnit.MINUTES.toMillis(1));
	  }

}
