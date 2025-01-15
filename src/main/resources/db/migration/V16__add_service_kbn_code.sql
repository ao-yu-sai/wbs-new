-- サービス区分コードカラムを追加
ALTER TABLE project ADD COLUMN service_kbn_code VARCHAR(50);

-- 既存のcategory_type_codeの値をservice_kbn_codeにコピー
UPDATE project SET service_kbn_code = category_type_code;

-- コメント追加
COMMENT ON COLUMN project.service_kbn_code IS 'サービス区分コード'; 