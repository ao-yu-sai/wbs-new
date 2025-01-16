package com.ai_offshore.tools.wbs.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ai_offshore.tools.wbs.web.model.ProjectFunction;
import com.ai_offshore.tools.wbs.web.model.ProjectFunctionTask;
import com.ai_offshore.tools.wbs.web.service.ProjectFunctionService;
import com.ai_offshore.tools.wbs.web.service.ProjectService;

@RestController
@RequestMapping("/projects")
public class ProjectFunctionController {

    @Autowired
    private ProjectFunctionService projectFunctionService;

    @Autowired
    private ProjectService projectService;

    @GetMapping("/{ticketNumber}/service-kbn")
    public String getServiceKbnCode(@PathVariable String ticketNumber, @RequestParam String serviceKbnCode) {
        return projectService.findById(ticketNumber, serviceKbnCode).getServiceKbnCode();
    }

    /**
     * 案件の機能一覧を取得
     */
    @GetMapping("/{ticketNumber}/project-functions")
    public List<ProjectFunction> getProjectFunctions(
            @PathVariable String ticketNumber,
            @RequestParam String serviceKbnCode) {
        return projectFunctionService.getProjectFunctions(ticketNumber, serviceKbnCode);
    }

    /**
     * 案件に機能を追加
     */
    @PostMapping("/{ticketNumber}/project-functions")
    public ResponseEntity<String> addProjectFunctions(
            @PathVariable String ticketNumber,
            @RequestParam String serviceKbnCode,
            @RequestParam(required = false) List<String> functionCodes) {
        try {
            projectFunctionService.addProjectFunctions(ticketNumber, serviceKbnCode, functionCodes != null ? functionCodes : List.of());
            return ResponseEntity.ok("機能を追加しました");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("機能の追加に失敗しました: " + e.getMessage());
        }
    }

    /**
     * 案件の機能を削除
     */
    @PostMapping("/delete-project-functions")
    public ResponseEntity<String> deleteProjectFunction(
            @RequestParam String ticketNumber,
            @RequestParam String serviceKbnCode,
            @RequestParam String functionCode) {
        try {
            projectFunctionService.deleteProjectFunction(ticketNumber, serviceKbnCode, functionCode);
            return ResponseEntity.ok("機能を削除しました");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("機能の削除に失敗しました: " + e.getMessage());
        }
    }

    /**
     * 機能に紐づくタスク一覧を取得
     */
    @GetMapping("/{ticketNumber}/functions/{functionCode}/tasks")
    public List<ProjectFunctionTask> getProjectFunctionTasks(
            @PathVariable String ticketNumber,
            @PathVariable String functionCode,
            @RequestParam String serviceKbnCode) {
        return projectFunctionService.getProjectFunctionTasks(ticketNumber, functionCode, serviceKbnCode);
    }

    /**
     * タスクを登録
     */
    @PostMapping("/project-function-tasks")
    public ResponseEntity<String> addProjectFunctionTask(
            @RequestParam String ticketNumber,
            @RequestParam String serviceKbnCode,
            @RequestParam String functionCode,
            @RequestParam String taskKbnCode) {
        try {
            projectFunctionService.addProjectFunctionTask(ticketNumber, serviceKbnCode, functionCode, taskKbnCode);
            return ResponseEntity.ok("タスクを登録しました");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("タスクの登録に失敗しました: " + e.getMessage());
        }
    }

    /**
     * タスク情報を更新
     */
    @PostMapping("/tasks/update")
    public ResponseEntity<String> updateTask(@RequestBody ProjectFunctionTask task) {
        try {
            projectFunctionService.updateTask(task);
            return ResponseEntity.ok("タスクを更新しました。");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("タスクの更新に失敗しました。");
        }
    }
} 