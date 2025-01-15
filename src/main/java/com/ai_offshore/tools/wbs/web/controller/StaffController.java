package com.ai_offshore.tools.wbs.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ai_offshore.tools.wbs.web.model.Staff;
import com.ai_offshore.tools.wbs.web.service.StaffService;

@Controller
@RequestMapping("/staff")
public class StaffController {
    
    private final StaffService staffService;
    
    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }
    
    @GetMapping
    public String list(Model model) {
        model.addAttribute("staffList", staffService.findAll());
        return "staff/list";
    }
    
    @GetMapping("/{id}")
    @ResponseBody
    public Staff getStaff(@PathVariable Long id) {
        return staffService.findById(id);
    }
    
    @PostMapping
    @ResponseBody
    public String create(@ModelAttribute Staff staff) {
        staff.setIsActive(true);
        staffService.create(staff);
        return "success";
    }
    
    @PostMapping("/{id}")
    @ResponseBody
    public String update(@PathVariable Long id, @ModelAttribute Staff staff) {
        staff.setStaffId(id);
        staffService.update(staff);
        return "success";
    }
    
    @PostMapping("/{id}/delete")
    @ResponseBody
    public String delete(@PathVariable Long id) {
        staffService.delete(id);
        return "success";
    }
} 