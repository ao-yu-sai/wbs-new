-- 既存のテーブル構造を確認
SELECT column_name, data_type 
FROM information_schema.columns 
WHERE table_name = 'project';

-- 既存のデータを一時テーブルにコピー
CREATE TEMP TABLE temp_project AS 
SELECT * FROM project;

-- 既存のテーブルを削除して再作成
DROP TABLE project;

CREATE TABLE project (
    project_id SERIAL PRIMARY KEY,
    project_code VARCHAR(50) NOT NULL,
    project_name VARCHAR(100) NOT NULL,
    description TEXT,
    project_type_code VARCHAR(50),
    category_type_code VARCHAR(50),
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- データを新しいテーブルに移行
INSERT INTO project (
    project_id,
    project_code,
    project_name,
    description,
    is_active
)
SELECT 
    project_id,
    COALESCE(project_code, 'CODE_' || project_id),
    project_name,
    description,
    COALESCE(is_active, true)
FROM temp_project;

-- インデックスを作成
CREATE INDEX idx_project_code ON project(project_code);
CREATE INDEX idx_project_type_code ON project(project_type_code);

-- コメント追加
COMMENT ON TABLE project IS '案件マスター';
COMMENT ON COLUMN project.project_id IS '案件ID';
COMMENT ON COLUMN project.project_code IS '案件コード';
COMMENT ON COLUMN project.project_name IS '案件名';
COMMENT ON COLUMN project.description IS '説明';
COMMENT ON COLUMN project.project_type_code IS '案件種別コード';
COMMENT ON COLUMN project.category_type_code IS '区分種別コード';
COMMENT ON COLUMN project.is_active IS '有効フラグ';
COMMENT ON COLUMN project.created_at IS '作成日時';
COMMENT ON COLUMN project.updated_at IS '更新日時'; 