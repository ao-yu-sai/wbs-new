package com.ai_offshore.tools.wbs.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ai_offshore.tools.wbs.web.model.CategoryType;

@Mapper
public interface CategoryTypeMapper {
    @Select("SELECT category_type_code, category_type_name, description, is_active " +
            "FROM category_type " +
            "ORDER BY category_type_code")
    List<CategoryType> findAll();

    @Select("SELECT category_type_code, category_type_name, description, is_active " +
            "FROM category_type " +
            "WHERE category_type_code = #{categoryTypeCode}")
    CategoryType findByCode(String categoryTypeCode);

    @Insert("INSERT INTO category_type (category_type_code, category_type_name, " +
            "description, is_active) " +
            "VALUES (#{categoryTypeCode}, #{categoryTypeName}, " +
            "#{description}, #{isActive})")
    void insert(CategoryType categoryType);

    @Update("UPDATE category_type SET " +
            "category_type_name = #{categoryTypeName}, " +
            "description = #{description}, " +
            "is_active = #{isActive} " +
            "WHERE category_type_code = #{categoryTypeCode}")
    void update(CategoryType categoryType);

    @Delete("DELETE FROM category_type " +
            "WHERE category_type_code = #{categoryTypeCode}")
    void delete(String categoryTypeCode);
} 