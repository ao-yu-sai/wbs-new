package com.ai_offshore.tools.wbs.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ai_offshore.tools.wbs.web.mapper.FunctionMapper;
import com.ai_offshore.tools.wbs.web.model.Function;

@RestController
@RequestMapping("/functions")
public class FunctionController {
    
    private final FunctionMapper functionMapper;
    
    public FunctionController(FunctionMapper functionMapper) {
        this.functionMapper = functionMapper;
    }
    
    @PostMapping
    public ResponseEntity<String> create(@ModelAttribute Function function) {
        try {
            functionMapper.insert(function);
            return ResponseEntity.ok("機能を登録しました");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("登録に失敗しました: " + e.getMessage());
        }
    }
} 