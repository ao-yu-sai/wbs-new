-- 一時的に外部キー制約を削除
ALTER TABLE IF EXISTS category
    DROP CONSTRAINT IF EXISTS category_category_type_id_fkey;

-- 既存のプライマリキー制約を削除
ALTER TABLE category_type 
    DROP CONSTRAINT category_type_pkey CASCADE;

-- 既存のデータを一時テーブルにコピー
CREATE TEMP TABLE temp_category_type AS 
SELECT * FROM category_type;

-- 既存のテーブルを削除して再作成
DROP TABLE category_type;

CREATE TABLE category_type (
    category_type_code VARCHAR(50) NOT NULL,
    category_type_name VARCHAR(100) NOT NULL,
    description TEXT,
    is_active BOOLEAN DEFAULT true
    PRIMARY KEY (category_type_code)
);

-- データを新しいテーブルに移行
INSERT INTO category_type (
    category_type_code,
    category_type_name,
    description,
    is_active
)
SELECT 
    category_type_code,
    category_type_name,
    description,
    is_active
FROM temp_category_type;

-- インデックスを作成
CREATE INDEX idx_category_type_name ON category_type(category_type_name);

-- categoryテーブルのcategory_type_id列を削除し、外部キー制約を更新
ALTER TABLE category
    DROP COLUMN category_type_id;

-- 外部キー制約を再作成
ALTER TABLE category
    ADD CONSTRAINT category_category_type_fkey 
    FOREIGN KEY (category_type_code) 
    REFERENCES category_type(category_type_code);

-- コメント追加
COMMENT ON TABLE category_type IS '区分種別マスター';
COMMENT ON COLUMN category_type.category_type_code IS '区分種別コード';
COMMENT ON COLUMN category_type.category_type_name IS '区分種別名';
COMMENT ON COLUMN category_type.description IS '説明';
COMMENT ON COLUMN category_type.is_active IS '有効フラグ'; 