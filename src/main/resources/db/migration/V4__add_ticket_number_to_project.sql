-- プロジェクトテーブルにチケット番号カラムを追加
ALTER TABLE project
ADD COLUMN ticket_number VARCHAR(50);

-- チケット番号にインデックスを追加（検索性能向上のため）
CREATE INDEX idx_project_ticket_number ON project(ticket_number);

-- コメント追加
COMMENT ON COLUMN project.ticket_number IS 'プロジェクトのチケット番号'; 