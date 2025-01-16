package com.ai_offshore.tools.wbs.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ai_offshore.tools.wbs.web.model.Project;

@Mapper
public interface ProjectMapper {
    
    @Select("SELECT p.*, c.category_name as \"category.categoryName\" " +
            "FROM project p " +
            "LEFT JOIN category c ON p.service_kbn_code = c.category_code " +
            "AND c.category_type_code = 'SERVICE_KBN' " +
            "ORDER BY p.ticket_number")
    List<Project> findAll();
    
    @Select("SELECT p.*, c.category_name as \"category.categoryName\" " +
            "FROM project p " +
            "LEFT JOIN category c ON p.service_kbn_code = c.category_code " +
            "AND c.category_type_code = 'SERVICE_KBN' " +
            "WHERE p.ticket_number = #{ticketNumber} AND p.service_kbn_code = #{serviceKbnCode}")
    Project findById(String ticketNumber,String serviceKbnCode);
    
    @Insert("INSERT INTO project (ticket_number, project_name, description, " +
            "project_code, service_kbn_code, is_active) " +
            "VALUES (#{ticketNumber}, #{projectName}, #{description}, " +
            "CONCAT('CODE_', #{ticketNumber}), #{serviceKbnCode}, #{isActive})")
    void insert(Project project);
    
    @Update("UPDATE project SET project_name = #{projectName}, description = #{description}, " +
            "service_kbn_code = #{serviceKbnCode}, " +
            "is_active = #{isActive} " +
            "WHERE ticket_number = #{ticketNumber}")
    void update(Project project);
    
    @Delete("DELETE FROM project WHERE ticket_number = #{ticketNumber}")
    void delete(String ticketNumber);
} 