package com.ai_offshore.tools.wbs.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ai_offshore.tools.wbs.web.client.RedmineClient;

@RestController
@RequestMapping("/api/redmine")
public class RedmineController {
    
    private final RedmineClient redmineClient;
    
    public RedmineController(RedmineClient redmineClient) {
        this.redmineClient = redmineClient;
    }
    
    @GetMapping("/issues/{issueId}")
    public String getIssue(@PathVariable String issueId) {
        return redmineClient.getIssue(issueId);
    }
} 