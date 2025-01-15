package com.ai_offshore.tools.wbs.web.model;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Project {
    private String ticketNumber;
    private String projectName;
    private String description;
    private String serviceKbnCode;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // 関連エンティティ
    private Category category;
} 