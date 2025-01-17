package com.ai_offshore.tools.wbs.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ai_offshore.tools.wbs.web.client.RedmineClient;
import com.ai_offshore.tools.wbs.web.model.RedmineIssue;

@RestController
@RequestMapping("/api/redmine")
public class RedmineController {
    
    private static final Logger logger = LoggerFactory.getLogger(RedmineController.class);
    
    private final RedmineClient redmineClient;
    
    public RedmineController(RedmineClient redmineClient) {
        this.redmineClient = redmineClient;
    }
    
    @GetMapping("/issues/{issueId}")
    public ResponseEntity<RedmineIssue> getIssue(@PathVariable String issueId) {
        try {
            logger.info("Fetching Redmine issue: {}", issueId);
            RedmineIssue issue = redmineClient.getIssue(issueId);
            
            if (issue != null && issue.getIssue() != null) {
                logger.info("Successfully retrieved issue: {}", issueId);
                return ResponseEntity.ok(issue);
            }
            
            logger.warn("Issue not found: {}", issueId);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error retrieving Redmine issue: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
} 