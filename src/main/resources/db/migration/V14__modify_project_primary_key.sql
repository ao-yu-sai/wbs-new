-- 一時テーブルにデータをコピー
CREATE TEMP TABLE temp_project AS 
SELECT 
    project_id,
    project_name,
    description,
    is_active,
    created_at,
    updated_at
FROM project;

-- 既存のテーブルを削除
DROP TABLE project CASCADE;

-- テーブルを再作成
CREATE TABLE project (
    ticket_number VARCHAR(50) PRIMARY KEY,
    project_code VARCHAR(50) NOT NULL,
    project_name VARCHAR(100) NOT NULL,
    description TEXT,
    project_type_code VARCHAR(50),
    category_type_code VARCHAR(50),
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- データを移行（NULLのチケット番号には一時的なIDを付与）
INSERT INTO project (
    ticket_number,
    project_code,
    project_name,
    description,
    is_active,
    created_at,
    updated_at
)
SELECT 
    'TICKET_' || t.project_id as ticket_number,
    'CODE_' || t.project_id as project_code,
    t.project_name,
    t.description,
    COALESCE(t.is_active, true),
    COALESCE(t.created_at, CURRENT_TIMESTAMP),
    COALESCE(t.updated_at, CURRENT_TIMESTAMP)
FROM temp_project t;

-- インデックスを作成
CREATE INDEX idx_project_code ON project(project_code);
CREATE INDEX idx_project_type_code ON project(project_type_code);

-- コメント追加
COMMENT ON TABLE project IS '案件マスター';
COMMENT ON COLUMN project.ticket_number IS 'チケット番号';
COMMENT ON COLUMN project.project_code IS '案件コード';
COMMENT ON COLUMN project.project_name IS '案件名';
COMMENT ON COLUMN project.description IS '説明';
COMMENT ON COLUMN project.project_type_code IS '案件種別コード';
COMMENT ON COLUMN project.category_type_code IS '区分種別コード';
COMMENT ON COLUMN project.is_active IS '有効フラグ';
COMMENT ON COLUMN project.created_at IS '作成日時';
COMMENT ON COLUMN project.updated_at IS '更新日時'; 