-- 機能情報マスター
CREATE TABLE function_info (
    function_id SERIAL PRIMARY KEY,
    category_id INTEGER NOT NULL REFERENCES category(category_id),
    function_name VARCHAR(100) NOT NULL,
    description TEXT,
    display_order INTEGER,
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- インデックス
CREATE INDEX idx_function_info_category_id ON function_info(category_id); 