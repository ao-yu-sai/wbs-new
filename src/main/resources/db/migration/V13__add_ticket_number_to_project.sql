ALTER TABLE project
ADD COLUMN ticket_number VARCHAR(50);

COMMENT ON COLUMN project.ticket_number IS 'チケット番号'; 