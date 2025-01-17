package com.ai_offshore.tools.wbs.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RedmineIssue {
    private Issue issue;
    
    @Data
    public static class Issue {
        private String id;
        private String subject;
        private String description;
        
        @JsonProperty("project")
        private Project project;
        
        @Data
        public static class Project {
            private String id;
            private String name;
        }
    }
} 