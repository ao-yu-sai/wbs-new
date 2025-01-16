-- 既存のテーブルを削除
DROP TABLE IF EXISTS project_function_info;

-- 必要な項目のみのテーブルを作成
CREATE TABLE project_function_info (
    ticket_number VARCHAR(20) NOT NULL,
    service_kbn_code VARCHAR(20) NOT NULL,
    function_code VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (ticket_number, service_kbn_code, function_code)
); 