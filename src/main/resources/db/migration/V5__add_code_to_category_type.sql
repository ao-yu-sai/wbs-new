-- 区分種別マスターに区分種別コード項目を追加
ALTER TABLE category_type
ADD COLUMN category_type_code VARCHAR(50);

-- コメント追加
COMMENT ON COLUMN category_type.category_type_code IS '区分種別コード';

-- 既存のデータに対してNULLを許可
ALTER TABLE category_type
ALTER COLUMN category_type_code SET DEFAULT NULL;

-- インデックス作成（検索性能向上のため）
CREATE INDEX idx_category_type_code ON category_type(category_type_code); 