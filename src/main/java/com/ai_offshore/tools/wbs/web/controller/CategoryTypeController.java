package com.ai_offshore.tools.wbs.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai_offshore.tools.wbs.web.model.Category;
import com.ai_offshore.tools.wbs.web.model.CategoryType;
import com.ai_offshore.tools.wbs.web.service.CategoryService;
import com.ai_offshore.tools.wbs.web.service.CategoryTypeService;

@Controller
@RequestMapping("/category-types")
public class CategoryTypeController {
    
    private static final Logger log = LoggerFactory.getLogger(CategoryTypeController.class);
    
    private final CategoryTypeService categoryTypeService;
    private final CategoryService categoryService;
    
    public CategoryTypeController(CategoryTypeService categoryTypeService, CategoryService categoryService) {
        this.categoryTypeService = categoryTypeService;
        this.categoryService = categoryService;
    }
    
    @GetMapping
    public String list(Model model) {
        model.addAttribute("categoryTypes", categoryTypeService.findAll());
        return "category/type/list";
    }
    
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("categoryType", new CategoryType());
        return "category/type/form";
    }
    
    @PostMapping
    public String create(@ModelAttribute CategoryType categoryType) {
        categoryTypeService.create(categoryType);
        return "redirect:/category-types";
    }
    
    @GetMapping("/{code}/edit")
    public String editForm(@PathVariable String code, Model model) {
        model.addAttribute("categoryType", categoryTypeService.findByCode(code));
        return "category/type/form";
    }
    
    @PostMapping("/{code}")
    public String update(@PathVariable String code, @ModelAttribute CategoryType categoryType) {
        categoryType.setCategoryTypeCode(code);
        categoryTypeService.update(categoryType);
        return "redirect:/category-types";
    }
    
    @PostMapping("/{code}/delete")
    public String delete(@PathVariable String code) {
        categoryTypeService.delete(code);
        return "redirect:/category-types";
    }
    
    @GetMapping("/{code}")
    @ResponseBody
    public CategoryType getDetail(@PathVariable String code) {
        return categoryTypeService.findByCode(code);
    }
    
    @PostMapping("/{code}/categories")
    @ResponseBody
    public String saveCategory(@PathVariable("code") String categoryTypeCode, @ModelAttribute Category category) {
        category.setCategoryTypeCode(categoryTypeCode);
        Category existingCategory = categoryService.findByTypeCodeAndCode(categoryTypeCode, category.getCategoryCode());
        if (existingCategory == null) {
            category.setIsActive(true);
            categoryService.create(category);
        } else {
            category.setIsActive(existingCategory.getIsActive());
            categoryService.update(category);
        }
        return "success";
    }
    
    @PostMapping("/{typeCode}/categories/{code}/delete")
    @ResponseBody
    public String deleteCategory(@PathVariable String typeCode, @PathVariable String code) {
        categoryService.delete(typeCode, code);
        return "success";
    }
    
    @GetMapping("/{code}/categories")
    public String getCategoryList(@PathVariable("code") String categoryTypeCode, Model model) {
        log.debug("Getting categories for type code: {}", categoryTypeCode);
        List<Category> categories = categoryService.findByCategoryTypeCode(categoryTypeCode);
        log.debug("Found {} categories", categories.size());
        model.addAttribute("categories", categories);
        return "category/type/category-list-fragment";
    }
} 