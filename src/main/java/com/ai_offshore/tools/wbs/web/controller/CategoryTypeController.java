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
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai_offshore.tools.wbs.web.model.Category;
import com.ai_offshore.tools.wbs.web.model.CategoryType;
import com.ai_offshore.tools.wbs.web.service.CategoryService;
import com.ai_offshore.tools.wbs.web.service.CategoryTypeService;

/**
 * 区分種別マスターのコントローラークラス
 */
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
    
    /**
     * 区分種別マスター一覧画面を表示
     */
    @GetMapping
    public String list(Model model) {
        model.addAttribute("categoryTypes", categoryTypeService.findAll());
        return "category/type/list";
    }
    
    /**
     * 区分種別マスター新規登録画面を表示
     */
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("categoryType", new CategoryType());
        return "category/type/form";
    }
    
    /**
     * 区分種別マスターを新規登録
     * POST /category-types
     */
    @PostMapping
    public String create(@ModelAttribute CategoryType categoryType) {
        categoryTypeService.create(categoryType);
        return "redirect:/category-types";
    }
    
    /**
     * 区分種別マスター編集画面を表示
     * GET /category-types/{code}/edit
     */
    @GetMapping("/{code}/edit")
    public String editForm(@PathVariable String code, Model model) {
        model.addAttribute("categoryType", categoryTypeService.findByCode(code));
        return "category/type/form";
    }
    
    /**
     * 区分種別マスターを更新
     * POST /category-types/{code}
     */
    @PostMapping("/{code}")
    public String update(@PathVariable String code, @ModelAttribute CategoryType categoryType) {
        categoryType.setCategoryTypeCode(code);
        categoryTypeService.update(categoryType);
        return "redirect:/category-types";
    }
    
    /**
     * 区分種別マスターを削除
     * POST /category-types/{code}/delete
     */
    @PostMapping("/{code}/delete")
    public String delete(@PathVariable String code) {
        categoryTypeService.delete(code);
        return "redirect:/category-types";
    }
    
    /**
     * 区分種別マスターの詳細情報を取得（API）
     * GET /category-types/{code}
     */
    @GetMapping("/{code}")
    @ResponseBody
    public CategoryType getDetail(@PathVariable String code) {
        return categoryTypeService.findByCode(code);
    }
    
    /**
     * 区分を保存（API）
     * POST /category-types/{code}/categories
     */
    @PostMapping("/{code}/categories")
    @ResponseBody
    public String saveCategory(@PathVariable("code") String categoryTypeCode, @ModelAttribute Category category) {
        category.setCategoryTypeCode(categoryTypeCode);
        Category existingCategory = categoryService.findByTypeCodeAndCode(categoryTypeCode, category.getCategoryCode());
        if (existingCategory == null) {
            // 新規登録の場合
            category.setIsActive(true);
            categoryService.create(category);
        } else {
            // 更新の場合
            category.setIsActive(existingCategory.getIsActive());
            categoryService.update(category);
        }
        return "success";
    }
    
    /**
     * 区分を削除（API）
     * POST /category-types/{typeCode}/categories/{code}/delete
     */
    @PostMapping("/{typeCode}/categories/{code}/delete")
    @ResponseBody
    public String deleteCategory(@PathVariable String typeCode, @PathVariable String code) {
        categoryService.delete(typeCode, code);
        return "success";
    }
    
    /**
     * 区分一覧を取得（フラグメント）
     * GET /category-types/{code}/categories
     */
    @GetMapping("/{code}/categories")
    public String getCategoryList(@PathVariable("code") String categoryTypeCode, Model model) {
        log.debug("Getting categories for type code: {}", categoryTypeCode);
        List<Category> categories = categoryService.findByCategoryTypeCode(categoryTypeCode);
        log.debug("Found {} categories", categories.size());
        model.addAttribute("categories", categories);
        return "category/type/category-list-fragment";
    }
} 