package com.ai_offshore.tools.wbs.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai_offshore.tools.wbs.web.mapper.ProjectFunctionMapper;
import com.ai_offshore.tools.wbs.web.mapper.ProjectMapper;
import com.ai_offshore.tools.wbs.web.model.ProjectFunction;
import com.ai_offshore.tools.wbs.web.model.ProjectFunctionTask;

@Service
public class ProjectFunctionService {

    @Autowired
    private ProjectFunctionMapper projectFunctionMapper;

    @Autowired
    private ProjectMapper projectMapper;

    /**
     * 案件の機能一覧を取得
     */
    public List<ProjectFunction> getProjectFunctions(String ticketNumber, String serviceKbnCode) {
        return projectFunctionMapper.findByTicketNumberAndServiceKbnCode(ticketNumber, serviceKbnCode);
    }

    /**
     * 案件に機能を追加
     */
    @Transactional
    public void addProjectFunctions(String ticketNumber, String serviceKbnCode, List<String> functionCodes) {
        // 既存の機能を削除
        // projectFunctionMapper.deleteByTicketNumberAndServiceKbnCode(ticketNumber, serviceKbnCode);
        
        // 新しい機能を追加（1機能1レコード）
        for (String functionCode : functionCodes) {
            projectFunctionMapper.insert(ticketNumber, serviceKbnCode, functionCode);
        }
    }

    /**
     * 案件の機能を削除
     */
    @Transactional
    public void deleteProjectFunction(String ticketNumber, String serviceKbnCode, String functionCode) {
        // 既存の機能を全て取得
        List<ProjectFunction> functions = projectFunctionMapper.findByTicketNumberAndServiceKbnCode(ticketNumber, serviceKbnCode);
        
        // 削除対象以外の機能を再登録
        projectFunctionMapper.deleteByTicketNumberAndServiceKbnCode(ticketNumber, serviceKbnCode);
        
        for (ProjectFunction function : functions) {
            if (!function.getFunctionCode().equals(functionCode)) {
                projectFunctionMapper.insert(ticketNumber, serviceKbnCode, function.getFunctionCode());
            }
        }
    }

    /**
     * 機能に紐づくタスク一覧を取得
     */
    public List<ProjectFunctionTask> getProjectFunctionTasks(String ticketNumber, String functionCode, String serviceKbnCode) {
        return projectFunctionMapper.findTasksByFunction(ticketNumber, functionCode, serviceKbnCode);
    }

    /**
     * タスクを登録
     */
    @Transactional
    public void addProjectFunctionTask(String ticketNumber, String serviceKbnCode, String functionCode, String taskKbnCode) {
        projectFunctionMapper.insertTask(ticketNumber, serviceKbnCode, functionCode, taskKbnCode);
    }

    @Transactional
    public void updateTask(ProjectFunctionTask task) {
        projectFunctionMapper.updateTask(task);
    }
} 