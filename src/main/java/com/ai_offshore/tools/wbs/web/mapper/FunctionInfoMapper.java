package com.ai_offshore.tools.wbs.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ai_offshore.tools.wbs.web.model.FunctionInfo;

@Mapper
public interface FunctionInfoMapper {
    
    @Select("SELECT * FROM function_info WHERE category_id = #{categoryId} ORDER BY display_order")
    List<FunctionInfo> findByCategoryId(Long categoryId);
    
    @Select("SELECT * FROM function_info WHERE function_id = #{functionId}")
    FunctionInfo findById(Long functionId);
    
    @Insert("INSERT INTO function_info (category_id, function_name, description, display_order, is_active) " +
            "VALUES (#{categoryId}, #{functionName}, #{description}, #{displayOrder}, #{isActive})")
    void insert(FunctionInfo functionInfo);
    
    @Update("UPDATE function_info SET function_name = #{functionName}, description = #{description}, " +
            "display_order = #{displayOrder}, is_active = #{isActive}, updated_at = CURRENT_TIMESTAMP " +
            "WHERE function_id = #{functionId}")
    void update(FunctionInfo functionInfo);
    
    @Delete("DELETE FROM function_info WHERE function_id = #{functionId}")
    void delete(Long functionId);
} 