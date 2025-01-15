-- 案件対象機能情報テーブルの作成
CREATE TABLE project_function_info (
    service_kbn_code VARCHAR(50) NOT NULL,
    ticket_number VARCHAR(50) NOT NULL,
    function_code VARCHAR(50) NOT NULL,
    task_kbn_code VARCHAR(50) NOT NULL,
    staff_email VARCHAR(256) NOT NULL,
    plan_start_date DATE,
    plan_end_date DATE,
    plan_man_hour DECIMAL(7,2),
    actual_start_date DATE,
    actual_end_date DATE,
    actual_man_hour DECIMAL(7,2),
    progress_rate INTEGER DEFAULT 0,
    delay_days INTEGER DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (service_kbn_code, ticket_number, function_code, task_kbn_code, staff_email)
);

-- インデックスの作成
CREATE INDEX idx_project_function_ticket ON project_function_info(ticket_number);
CREATE INDEX idx_project_function_staff ON project_function_info(staff_email);

-- 外部キー制約の追加
ALTER TABLE project_function_info
    ADD CONSTRAINT fk_project_function_project
    FOREIGN KEY (ticket_number)
    REFERENCES project_info(ticket_number);

ALTER TABLE project_function_info
    ADD CONSTRAINT fk_project_function_function
    FOREIGN KEY (service_kbn_code, function_code)
    REFERENCES function_info(service_kbn_code, function_code);

ALTER TABLE project_function_info
    ADD CONSTRAINT fk_project_function_task
    FOREIGN KEY (task_kbn_code)
    REFERENCES category(category_code);

ALTER TABLE project_function_info
    ADD CONSTRAINT fk_project_function_staff
    FOREIGN KEY (staff_email)
    REFERENCES staff_info(email);

-- コメント追加
COMMENT ON TABLE project_function_info IS '案件対象機能情報';
COMMENT ON COLUMN project_function_info.service_kbn_code IS 'サービス区分コード';
COMMENT ON COLUMN project_function_info.ticket_number IS 'チケット番号';
COMMENT ON COLUMN project_function_info.function_code IS '機能コード';
COMMENT ON COLUMN project_function_info.task_kbn_code IS 'タスク区分コード';
COMMENT ON COLUMN project_function_info.staff_email IS '担当者メールアドレス';
COMMENT ON COLUMN project_function_info.plan_start_date IS '予定開始日';
COMMENT ON COLUMN project_function_info.plan_end_date IS '予定終了日';
COMMENT ON COLUMN project_function_info.plan_man_hour IS '予定工数';
COMMENT ON COLUMN project_function_info.actual_start_date IS '実績開始日';
COMMENT ON COLUMN project_function_info.actual_end_date IS '実績終了日';
COMMENT ON COLUMN project_function_info.actual_man_hour IS '実績工数';
COMMENT ON COLUMN project_function_info.progress_rate IS '進捗率';
COMMENT ON COLUMN project_function_info.delay_days IS '遅延日数';
COMMENT ON COLUMN project_function_info.created_at IS '作成日時';
COMMENT ON COLUMN project_function_info.updated_at IS '更新日時'; 