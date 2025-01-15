-- 一時テーブルにデータをコピー
CREATE TEMP TABLE temp_function_info AS 
SELECT * FROM function_info;

-- 既存のテーブルを削除
DROP TABLE function_info CASCADE;

-- テーブルを再作成
CREATE TABLE function_info (
    service_kbn_code VARCHAR(50) NOT NULL,
    function_code VARCHAR(50) NOT NULL,
    function_name VARCHAR(100) NOT NULL,
    description TEXT,
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (service_kbn_code, function_code)
);

-- データを移行（既存のfunction_idから新しいコードを生成）
INSERT INTO function_info (
    service_kbn_code,
    function_code,
    function_name,
    description,
    is_active,
    created_at,
    updated_at
)
SELECT 
    COALESCE(t.service_kbn_code, 'DEFAULT'),
    'FUNC_' || t.function_id as function_code,
    t.function_name,
    t.description,
    COALESCE(t.is_active, true),
    COALESCE(t.created_at, CURRENT_TIMESTAMP),
    COALESCE(t.updated_at, CURRENT_TIMESTAMP)
FROM temp_function_info t;

-- インデックスを作成
CREATE INDEX idx_function_code ON function_info(function_code);
CREATE INDEX idx_service_kbn_code ON function_info(service_kbn_code);

-- コメント追加
COMMENT ON TABLE function_info IS '機能情報マスタ';
COMMENT ON COLUMN function_info.service_kbn_code IS 'サービス区分コード';
COMMENT ON COLUMN function_info.function_code IS '機能コード';
COMMENT ON COLUMN function_info.function_name IS '機能名';
COMMENT ON COLUMN function_info.description IS '説明';
COMMENT ON COLUMN function_info.is_active IS '有効フラグ';
COMMENT ON COLUMN function_info.created_at IS '作成日時';
COMMENT ON COLUMN function_info.updated_at IS '更新日時'; 