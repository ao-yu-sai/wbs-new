package com.ai_offshore.tools.wbs.web.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai_offshore.tools.wbs.web.mapper.FunctionMapper;
import com.ai_offshore.tools.wbs.web.model.Function;
import com.ai_offshore.tools.wbs.web.service.CategoryService;

@Controller
@RequestMapping("/master/functions")
public class FunctionMasterController {
    
    private final FunctionMapper functionMapper;
    private final CategoryService categoryService;
    
    public FunctionMasterController(FunctionMapper functionMapper, CategoryService categoryService) {
        this.functionMapper = functionMapper;
        this.categoryService = categoryService;
    }
    
    @GetMapping
    public String list(Model model) {
        List<Function> functions = functionMapper.findAll();
        model.addAttribute("functions", functions);
        model.addAttribute("categories", categoryService.findByCategoryTypeCode("SERVICE_KBN"));
        return "master/function/list";
    }
    
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Function> getFunction(@PathVariable String id) {
        try {
            Function function = functionMapper.findById(id);
            if (function != null) {
                return ResponseEntity.ok(function);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PostMapping
    @ResponseBody
    public ResponseEntity<String> create(@ModelAttribute Function function) {
        try {
            functionMapper.insert(function);
            return ResponseEntity.ok("機能を登録しました");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("登録に失敗しました: " + e.getMessage());
        }
    }
    
    @PostMapping("/{id}")
    @ResponseBody
    public ResponseEntity<String> update(@PathVariable String id, @ModelAttribute Function function) {
        try {
            function.setFunctionCode(id);
            functionMapper.update(function);
            return ResponseEntity.ok("機能を更新しました");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("更新に失敗しました: " + e.getMessage());
        }
    }
    
    @PostMapping("/{id}/delete")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable String id) {
        try {
            functionMapper.delete(id);
            return ResponseEntity.ok("機能を削除しました");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("削除に失敗しました: " + e.getMessage());
        }
    }
} 