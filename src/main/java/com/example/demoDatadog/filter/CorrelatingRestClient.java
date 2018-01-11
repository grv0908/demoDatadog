package com.example.demoDatadog.filter;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CorrelatingRestClient implements RestClient {

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public String getForString(String uri) {
        String traceId = RequestCorrelation.getTraceId();
        String spanId = RequestCorrelation.getSpanId();
        
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(RequestCorrelation.TRACE_ID, traceId);
        httpHeaders.set(RequestCorrelation.SPAN_ID, spanId);

        System.out.println("********* Start REST request to "+uri+" with ID " + traceId);

        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET,
                new HttpEntity<String>(httpHeaders), String.class);

       return response.getBody();
    }
}