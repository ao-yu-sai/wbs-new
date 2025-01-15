package com.ai_offshore.tools.wbs.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.ai_offshore.tools.wbs.web.model.ProjectFunction;

@Mapper
public interface ProjectFunctionMapper {
    
    @Select("SELECT pf.*, " +
            "f.function_name as \"function.functionName\", " +
            "f.description as \"function.description\", " +
            "c.category_name as \"taskCategory.categoryName\", " +
            "s.staff_name as \"staff.staffName\" " +
            "FROM project_function_info pf " +
            "LEFT JOIN function_info f ON pf.service_kbn_code = f.service_kbn_code " +
            "AND pf.function_code = f.function_code " +
            "LEFT JOIN category c ON pf.task_kbn_code = c.category_code " +
            "LEFT JOIN staff_info s ON pf.staff_email = s.email " +
            "WHERE pf.ticket_number = #{ticketNumber} " +
            "ORDER BY pf.function_code, pf.task_kbn_code")
    List<ProjectFunction> findByTicketNumber(String ticketNumber);
} 