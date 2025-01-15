package com.ai_offshore.tools.wbs.web.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai_offshore.tools.wbs.web.mapper.FunctionInfoMapper;
import com.ai_offshore.tools.wbs.web.model.FunctionInfo;

@Service
public class FunctionInfoService {
    
    private final FunctionInfoMapper functionInfoMapper;
    
    public FunctionInfoService(FunctionInfoMapper functionInfoMapper) {
        this.functionInfoMapper = functionInfoMapper;
    }
    
    public List<FunctionInfo> findByCategoryId(Long categoryId) {
        return functionInfoMapper.findByCategoryId(categoryId);
    }
    
    public FunctionInfo findById(Long id) {
        return functionInfoMapper.findById(id);
    }
    
    @Transactional
    public void create(FunctionInfo functionInfo) {
        functionInfoMapper.insert(functionInfo);
    }
    
    @Transactional
    public void update(FunctionInfo functionInfo) {
        functionInfoMapper.update(functionInfo);
    }
    
    @Transactional
    public void delete(Long id) {
        functionInfoMapper.delete(id);
    }
} 