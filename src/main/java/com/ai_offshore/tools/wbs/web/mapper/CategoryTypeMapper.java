package com.ai_offshore.tools.wbs.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ai_offshore.tools.wbs.web.model.CategoryType;

@Mapper
public interface CategoryTypeMapper {
    List<CategoryType> findAll();
    CategoryType findByCode(String categoryTypeCode);
    void insert(CategoryType categoryType);
    void update(CategoryType categoryType);
    void delete(String categoryTypeCode);
} 