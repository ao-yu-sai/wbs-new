package com.ai_offshore.tools.wbs.web.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai_offshore.tools.wbs.web.mapper.ProjectMapper;
import com.ai_offshore.tools.wbs.web.model.Project;

@Service
public class ProjectService {
    
    private final ProjectMapper projectMapper;
    
    public ProjectService(ProjectMapper projectMapper) {
        this.projectMapper = projectMapper;
    }
    
    public List<Project> findAll() {
        return projectMapper.findAll();
    }
    
    public Project findById(String id,String serviceKbnCode) {
        return projectMapper.findById(id,serviceKbnCode);
    }
    
    @Transactional
    public void create(Project project) {
        if (project.getTicketNumber() == null || project.getTicketNumber().isEmpty()) {
            String timestamp = String.valueOf(System.currentTimeMillis());
            project.setTicketNumber("TICKET_" + timestamp);
        }
        project.setIsActive(true);
        projectMapper.insert(project);
    }
    
    @Transactional
    public void update(Project project) {
        projectMapper.update(project);
    }
    
    @Transactional
    public void delete(String id) {
        projectMapper.delete(id);
    }
} 