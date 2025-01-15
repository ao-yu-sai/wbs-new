package com.ai_offshore.tools.wbs.web.model;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Function {
    private String serviceKbnCode;
    private String functionCode;
    private String functionName;
    private String description;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Category category;
} 