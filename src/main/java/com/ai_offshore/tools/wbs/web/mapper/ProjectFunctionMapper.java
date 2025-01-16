package com.ai_offshore.tools.wbs.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ai_offshore.tools.wbs.web.model.ProjectFunction;
import com.ai_offshore.tools.wbs.web.model.ProjectFunctionTask;

@Mapper
public interface ProjectFunctionMapper {

    /**
     * 案件の機能一覧を取得
     */
    List<ProjectFunction> findByTicketNumberAndServiceKbnCode(
        @Param("ticketNumber") String ticketNumber,
        @Param("serviceKbnCode") String serviceKbnCode);

    /**
     * 案件の機能を削除
     */
    void deleteByTicketNumberAndServiceKbnCode(
        @Param("ticketNumber") String ticketNumber,
        @Param("serviceKbnCode") String serviceKbnCode);

    /**
     * 案件の機能を追加
     */
    void insert(
        @Param("ticketNumber") String ticketNumber,
        @Param("serviceKbnCode") String serviceKbnCode,
        @Param("functionCode") String functionCode);

    void insertBatch(
        @Param("ticketNumber") String ticketNumber,
        @Param("serviceKbnCode") String serviceKbnCode,
        @Param("functionCodes") List<String> functionCodes);

    /**
     * 機能に紐づくタスク一覧を取得
     */
    @Select("SELECT * FROM project_function_task_info " +
            "WHERE ticket_number = #{ticketNumber} " +
            "AND function_code = #{functionCode} " +
            "AND service_kbn_code = #{serviceKbnCode}")
    List<ProjectFunctionTask> findTasksByFunction(
            @Param("ticketNumber") String ticketNumber,
            @Param("functionCode") String functionCode,
            @Param("serviceKbnCode") String serviceKbnCode);
} 