package com.ai_offshore.tools.wbs.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ai_offshore.tools.wbs.web.model.Staff;
import com.ai_offshore.tools.wbs.web.service.StaffService;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @GetMapping("/list")
    public List<Staff> getStaffList() {
        return staffService.findAll();
    }
} 