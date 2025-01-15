package com.ai_offshore.tools.wbs.web.client;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class RedmineClient {
    
    @Value("${redmine.url}")
    private String redmineUrl;
    
    @Value("${redmine.api-key}")
    private String apiKey;
    
    private final RestTemplate restTemplate;
    
    public RedmineClient() {
        this.restTemplate = new RestTemplate();
    }
    
    public String getIssue(String issueId) {
        URI uri = UriComponentsBuilder
            .fromUriString(redmineUrl)
            .path("/issues/{issueId}.json")
            .buildAndExpand(issueId)
            .toUri();
            
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Redmine-API-Key", apiKey);
        
        RequestEntity<?> request = new RequestEntity<>(headers, HttpMethod.GET, uri);
        ResponseEntity<String> response = restTemplate.exchange(request, String.class);
        
        return response.getBody();
    }
} 