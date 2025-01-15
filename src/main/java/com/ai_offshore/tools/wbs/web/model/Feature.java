package com.ai_offshore.tools.wbs.web.model;

import lombok.Data;

@Data
public class Feature {
    private Long featureId;
    private Long projectId;
    private String featureName;
    private String description;
    private Double progressRate;
    private String status;
} 