-- 一時的に外部キー制約を削除
ALTER TABLE IF EXISTS function_info 
    DROP CONSTRAINT IF EXISTS function_info_category_id_fkey;

ALTER TABLE IF EXISTS task
    DROP CONSTRAINT IF EXISTS task_category_id_fkey;

ALTER TABLE IF EXISTS project_category
    DROP CONSTRAINT IF EXISTS project_category_category_id_fkey;

-- 既存のプライマリキー制約を削除
ALTER TABLE category 
    DROP CONSTRAINT category_pkey CASCADE;

-- 既存のデータを一時テーブルにコピー
CREATE TEMP TABLE temp_category AS 
SELECT * FROM category;

-- 既存のテーブルを削除して再作成
DROP TABLE category;

CREATE TABLE category (
    category_type_id INTEGER NOT NULL,
    category_name VARCHAR(100) NOT NULL,
    category_code VARCHAR(50) NOT NULL,
    category_type_code VARCHAR(50) NOT NULL,
    description TEXT,
    display_order INTEGER,
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (category_type_code, category_code)
);

-- データを新しいテーブルに移行（created_atとupdated_atを除外）
INSERT INTO category (
    category_type_id, category_name, category_code, 
    category_type_code, description, display_order, 
    is_active
)
SELECT 
    category_type_id, category_name, category_code, 
    category_type_code, description, display_order, 
    is_active
FROM temp_category;

-- インデックスを作成
CREATE INDEX idx_category_composite ON category(category_type_code, category_code);

-- 外部キー制約を再作成
ALTER TABLE IF EXISTS task
    ADD CONSTRAINT task_category_fkey 
    FOREIGN KEY (category_type_code, category_code) 
    REFERENCES category(category_type_code, category_code);

ALTER TABLE IF EXISTS project_category
    ADD CONSTRAINT project_category_fkey 
    FOREIGN KEY (category_type_code, category_code) 
    REFERENCES category(category_type_code, category_code);

ALTER TABLE IF EXISTS function_info 
    ADD CONSTRAINT function_info_category_fkey 
    FOREIGN KEY (category_type_code, category_code) 
    REFERENCES category(category_type_code, category_code);

-- コメント追加
COMMENT ON TABLE category IS '区分マスター';
COMMENT ON COLUMN category.category_type_code IS '区分種別コード';
COMMENT ON COLUMN category.category_code IS '区分コード';
COMMENT ON COLUMN category.category_name IS '区分名';
COMMENT ON COLUMN category.description IS '説明';
COMMENT ON COLUMN category.display_order IS '表示順';
COMMENT ON COLUMN category.is_active IS '有効フラグ'; 