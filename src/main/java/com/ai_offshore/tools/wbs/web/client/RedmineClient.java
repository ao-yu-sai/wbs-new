package com.ai_offshore.tools.wbs.web.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.ai_offshore.tools.wbs.web.model.RedmineIssue;

@Component
public class RedmineClient {
    
    private static final Logger logger = LoggerFactory.getLogger(RedmineClient.class);
    
    @Value("${redmine.api.url}")
    private String redmineUrl;
    
    @Value("${redmine.api.key}")
    private String apiKey;
    
    private final RestTemplate restTemplate;
    
    public RedmineClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    public RedmineIssue getIssue(String ticketNumber) {
        try {
            String baseUrl = redmineUrl.endsWith("/") ? redmineUrl : redmineUrl + "/";
            String url = String.format("%sissues/%s.json?key=%s", baseUrl, ticketNumber, apiKey);
            
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            headers.set("Accept", "application/json");
            
            HttpEntity<?> entity = new HttpEntity<>(headers);
            
            logger.debug("Requesting Redmine API: {} with headers: {}", url, headers);
            
            ResponseEntity<RedmineIssue> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                RedmineIssue.class
            );
            
            logger.debug("Redmine API response status: {}", response.getStatusCode());
            logger.debug("Redmine API response body: {}", response.getBody());
            
            return response.getBody();
        } catch (Exception e) {
            logger.error("Error calling Redmine API: {}", e.getMessage(), e);
            throw e;
        }
    }
} 