-- 既存のデータを一時テーブルにコピー
CREATE TEMP TABLE temp_category AS 
SELECT * FROM category;

-- 既存のテーブルを削除して再作成
DROP TABLE category;

CREATE TABLE category (
    category_type_code VARCHAR(50) NOT NULL,
    category_code VARCHAR(50) NOT NULL,
    category_name VARCHAR(100) NOT NULL,
    description TEXT,
    display_order INTEGER DEFAULT 0,
    is_active BOOLEAN DEFAULT true,
    PRIMARY KEY (category_type_code, category_code)
);

-- データを新しいテーブルに移行
INSERT INTO category (
    category_type_code,
    category_code,
    category_name,
    description,
    display_order,
    is_active
)
SELECT 
    category_type_code,
    category_code,
    category_name,
    description,
    display_order,
    is_active
FROM temp_category;

-- インデックスを作成
CREATE INDEX idx_category_name ON category(category_name);
CREATE INDEX idx_category_type_code ON category(category_type_code);
CREATE INDEX idx_category_code ON category(category_code);

-- コメント追加
COMMENT ON TABLE category IS '区分マスター';
COMMENT ON COLUMN category.category_type_code IS '区分種別コード';
COMMENT ON COLUMN category.category_code IS '区分コード';
COMMENT ON COLUMN category.category_name IS '区分名';
COMMENT ON COLUMN category.description IS '説明';
COMMENT ON COLUMN category.display_order IS '表示順';
COMMENT ON COLUMN category.is_active IS '有効フラグ'; 