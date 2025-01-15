-- 区分テーブルに区分種別コードカラムを追加
ALTER TABLE category
ADD COLUMN category_type_code VARCHAR(50) NOT NULL DEFAULT 'TEMP_TYPE_CODE';

-- コメント追加
COMMENT ON COLUMN category.category_type_code IS '区分種別コード';

-- インデックス作成（検索性能向上のため）
CREATE INDEX idx_category_type_code ON category(category_type_code); 