-- 区分マスターに区分コード列を追加
ALTER TABLE category
ADD COLUMN category_code VARCHAR(50) NOT NULL DEFAULT 'temp_kbn';

-- コメント追加
COMMENT ON COLUMN category.category_code IS '区分コード';

-- インデックス作成（検索性能向上のため）
CREATE INDEX idx_category_code ON category(category_code); 