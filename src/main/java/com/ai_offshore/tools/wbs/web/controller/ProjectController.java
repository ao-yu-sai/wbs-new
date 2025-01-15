package com.ai_offshore.tools.wbs.web.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai_offshore.tools.wbs.web.mapper.FunctionMapper;
import com.ai_offshore.tools.wbs.web.mapper.ProjectFunctionMapper;
import com.ai_offshore.tools.wbs.web.model.Category;
import com.ai_offshore.tools.wbs.web.model.Function;
import com.ai_offshore.tools.wbs.web.model.Project;
import com.ai_offshore.tools.wbs.web.model.ProjectFunction;
import com.ai_offshore.tools.wbs.web.service.CategoryService;
import com.ai_offshore.tools.wbs.web.service.ProjectService;

@Controller
@RequestMapping("/projects")
public class ProjectController {
    
    private final ProjectService projectService;
    private final CategoryService categoryService;
    private final FunctionMapper functionMapper;
    private final ProjectFunctionMapper projectFunctionMapper;
    
    public ProjectController(ProjectService projectService, CategoryService categoryService, FunctionMapper functionMapper, ProjectFunctionMapper projectFunctionMapper) {
        this.projectService = projectService;
        this.categoryService = categoryService;
        this.functionMapper = functionMapper;
        this.projectFunctionMapper = projectFunctionMapper;
    }
    
    @GetMapping
    public String list(Model model) {
        List<Category> categories = categoryService.findByCategoryTypeCode("SERVICE_KBN");
        model.addAttribute("categories", categories);
        model.addAttribute("projects", projectService.findAll());
        return "project/list";
    }
    
    @PostMapping
    @ResponseBody
    public ResponseEntity<String> create(@ModelAttribute Project project) {
        try {
            projectService.create(project);
            return ResponseEntity.ok("案件を登録しました");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("登録に失敗しました: " + e.getMessage());
        }
    }
    
    @GetMapping("/{id}/edit")
    @ResponseBody
    public ResponseEntity<Project> getProjectForEdit(@PathVariable String id) {
        try {
            Project project = projectService.findById(id);
            if (project != null) {
                return ResponseEntity.ok(project);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute Project project) {
        project.setTicketNumber(id);
        projectService.update(project);
        return "redirect:/projects";
    }
    
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        projectService.delete(id);
        return "redirect:/projects";
    }
    
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Project> getProject(@PathVariable String id) {
        try {
            Project project = projectService.findById(id);
            if (project != null) {
                return ResponseEntity.ok(project);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/{id}/functions")
    @ResponseBody
    public ResponseEntity<List<ProjectFunction>> getFunctions(@PathVariable String id) {
        try {
            Project project = projectService.findById(id);
            if (project != null && project.getServiceKbnCode() != null) {
                List<ProjectFunction> functions = projectFunctionMapper.findByTicketNumber(id);
                return ResponseEntity.ok(functions);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/{id}/service-kbn")
    @ResponseBody
    public ResponseEntity<String> getServiceKbnCode(@PathVariable String id) {
        try {
            Project project = projectService.findById(id);
            if (project != null && project.getServiceKbnCode() != null) {
                return ResponseEntity.ok(project.getServiceKbnCode());
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/{id}/available-functions")
    @ResponseBody
    public ResponseEntity<List<Function>> getAvailableFunctions(@PathVariable String id) {
        try {
            Project project = projectService.findById(id);
            if (project != null && project.getServiceKbnCode() != null) {
                List<Function> functions = functionMapper.findByServiceKbnCode(project.getServiceKbnCode());
                return ResponseEntity.ok(functions);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
} 