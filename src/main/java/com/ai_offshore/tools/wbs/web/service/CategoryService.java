package com.ai_offshore.tools.wbs.web.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ai_offshore.tools.wbs.web.mapper.CategoryMapper;
import com.ai_offshore.tools.wbs.web.model.Category;

@Service
public class CategoryService {
    
    private final CategoryMapper categoryMapper;
    
    public CategoryService(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }
    
    public List<Category> findByCategoryTypeCode(String categoryTypeCode) {
        return categoryMapper.findByCategoryTypeCode(categoryTypeCode);
    }
    
    public Category findByTypeCodeAndCode(String categoryTypeCode, String categoryCode) {
        return categoryMapper.findByTypeCodeAndCode(categoryTypeCode, categoryCode);
    }
    
    public void create(Category category) {
        category.setIsActive(true);
        categoryMapper.insert(category);
    }
    
    public void update(Category category) {
        if (category.getNewCategoryCode() == null) {
            category.setNewCategoryCode(category.getCategoryCode());
        }
        categoryMapper.update(category);
    }
    
    public void delete(String categoryTypeCode, String categoryCode) {
        categoryMapper.delete(categoryTypeCode, categoryCode);
    }
} 