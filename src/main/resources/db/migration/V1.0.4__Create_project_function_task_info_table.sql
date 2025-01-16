-- 案件機能別タスク情報テーブル
CREATE TABLE project_function_task_info (
    service_kbn_code VARCHAR(20) NOT NULL,
    ticket_number VARCHAR(20) NOT NULL,
    function_code VARCHAR(20) NOT NULL,
    task_kbn_code VARCHAR(20) NOT NULL,
    person_in_charge VARCHAR(50),
    planned_start_date DATE,
    planned_end_date DATE,
    planned_man_hours DECIMAL(5,2),
    actual_start_date DATE,
    actual_end_date DATE,
    actual_man_hours DECIMAL(5,2),
    progress_rate INTEGER,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (service_kbn_code, ticket_number, function_code, task_kbn_code),
    FOREIGN KEY (service_kbn_code, ticket_number, function_code) 
        REFERENCES project_function_info (service_kbn_code, ticket_number, function_code)
        ON DELETE CASCADE
);

-- カラムコメントの追加
COMMENT ON TABLE project_function_task_info IS '案件機能別タスク情報';
COMMENT ON COLUMN project_function_task_info.service_kbn_code IS 'サービス区分コード';
COMMENT ON COLUMN project_function_task_info.ticket_number IS 'チケット番号';
COMMENT ON COLUMN project_function_task_info.function_code IS '機能コード';
COMMENT ON COLUMN project_function_task_info.task_kbn_code IS 'タスク区分コード';
COMMENT ON COLUMN project_function_task_info.person_in_charge IS '担当者';
COMMENT ON COLUMN project_function_task_info.planned_start_date IS '開始予定日';
COMMENT ON COLUMN project_function_task_info.planned_end_date IS '終了予定日';
COMMENT ON COLUMN project_function_task_info.planned_man_hours IS '予定工数';
COMMENT ON COLUMN project_function_task_info.actual_start_date IS '実績開始日';
COMMENT ON COLUMN project_function_task_info.actual_end_date IS '実績終了日';
COMMENT ON COLUMN project_function_task_info.actual_man_hours IS '実績工数';
COMMENT ON COLUMN project_function_task_info.progress_rate IS '進捗率';
COMMENT ON COLUMN project_function_task_info.created_at IS '作成日時';
COMMENT ON COLUMN project_function_task_info.updated_at IS '更新日時'; 