package com.ai_offshore.tools.wbs.web.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ProjectFunctionTask {
    private String serviceKbnCode;
    private String ticketNumber;
    private String functionCode;
    private String taskKbnCode;
    private String taskName;
    private String personInCharge;
    private LocalDate plannedStartDate;
    private LocalDate plannedEndDate;
    private BigDecimal plannedManHours;
    private LocalDate actualStartDate;
    private LocalDate actualEndDate;
    private BigDecimal actualManHours;
    private Integer progressRate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 