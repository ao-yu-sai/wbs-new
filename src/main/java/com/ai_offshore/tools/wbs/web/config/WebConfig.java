package com.ai_offshore.tools.wbs.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WebConfig {
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
} 