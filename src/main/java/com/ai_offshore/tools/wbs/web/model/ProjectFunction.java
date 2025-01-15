package com.ai_offshore.tools.wbs.web.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectFunction {
    private String serviceKbnCode;
    private String ticketNumber;
    private String functionCode;
    private String taskKbnCode;
    private String staffEmail;
    private LocalDate planStartDate;
    private LocalDate planEndDate;
    private BigDecimal planManHour;
    private LocalDate actualStartDate;
    private LocalDate actualEndDate;
    private BigDecimal actualManHour;
    private Integer progressRate;
    private Integer delayDays;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // 関連エンティティ
    private Project project;
    private Function function;
    private Category taskCategory;
    private Staff staff;
} 