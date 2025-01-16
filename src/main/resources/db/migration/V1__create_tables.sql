-- 担当者マスター
CREATE TABLE staff (
    staff_id serial4 NOT NULL,
    staff_name varchar(100) NOT NULL,
    email varchar(255) NOT NULL UNIQUE,
    department varchar(100) NOT NULL,
    is_active bool DEFAULT true NULL,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT staff_pkey PRIMARY KEY (staff_id)
);

COMMENT ON TABLE staff IS '担当者マスター';
COMMENT ON COLUMN staff.staff_id IS '担当者ID';
COMMENT ON COLUMN staff.staff_name IS '担当者名';
COMMENT ON COLUMN staff.email IS 'メールアドレス';
COMMENT ON COLUMN staff.department IS '所属部署';
COMMENT ON COLUMN staff.is_active IS '有効フラグ';
COMMENT ON COLUMN staff.created_at IS '作成日時';
COMMENT ON COLUMN staff.updated_at IS '更新日時';

CREATE INDEX idx_staff_email ON staff USING btree (email);

-- 区分種別マスター
CREATE TABLE category_type (
    category_type_code varchar(50) NOT NULL,
    category_type_name varchar(100) NOT NULL,
    description text NULL,
    is_active bool DEFAULT true NULL,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT category_type_pkey PRIMARY KEY (category_type_code)
);

COMMENT ON TABLE category_type IS '区分種別マスター';
COMMENT ON COLUMN category_type.category_type_code IS '区分種別コード';
COMMENT ON COLUMN category_type.category_type_name IS '区分種別名';
COMMENT ON COLUMN category_type.description IS '説明';
COMMENT ON COLUMN category_type.is_active IS '有効フラグ';
COMMENT ON COLUMN category_type.created_at IS '作成日時';
COMMENT ON COLUMN category_type.updated_at IS '更新日時';

CREATE INDEX idx_category_type_name ON category_type USING btree (category_type_name);

-- 区分マスター
CREATE TABLE category (
    category_type_code varchar(50) NOT NULL,
    category_code varchar(50) NOT NULL,
    category_name varchar(100) NOT NULL,
    description text NULL,
    display_order int4 DEFAULT 0 NULL,
    is_active bool DEFAULT true NULL,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT category_pkey PRIMARY KEY (category_type_code, category_code)
);

COMMENT ON TABLE category IS '区分マスター';
COMMENT ON COLUMN category.category_type_code IS '区分種別コード';
COMMENT ON COLUMN category.category_code IS '区分コード';
COMMENT ON COLUMN category.category_name IS '区分名';
COMMENT ON COLUMN category.description IS '説明';
COMMENT ON COLUMN category.display_order IS '表示順';
COMMENT ON COLUMN category.is_active IS '有効フラグ';
COMMENT ON COLUMN category.created_at IS '作成日時';
COMMENT ON COLUMN category.updated_at IS '更新日時';

CREATE INDEX idx_category_code ON category USING btree (category_code);
CREATE INDEX idx_category_name ON category USING btree (category_name);
CREATE INDEX idx_category_type_code ON category USING btree (category_type_code);

-- 機能情報マスター
CREATE TABLE function_info (
    service_kbn_code varchar(50) NOT NULL,
    function_code varchar(50) NOT NULL,
    function_name varchar(100) NOT NULL,
    description text NULL,
    is_active bool DEFAULT true NULL,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT function_info_pkey PRIMARY KEY (service_kbn_code, function_code)
);

COMMENT ON TABLE function_info IS '機能情報マスター';
COMMENT ON COLUMN function_info.service_kbn_code IS 'サービス区分コード';
COMMENT ON COLUMN function_info.function_code IS '機能コード';
COMMENT ON COLUMN function_info.function_name IS '機能名';
COMMENT ON COLUMN function_info.description IS '説明';
COMMENT ON COLUMN function_info.is_active IS '有効フラグ';
COMMENT ON COLUMN function_info.created_at IS '作成日時';
COMMENT ON COLUMN function_info.updated_at IS '更新日時';

CREATE INDEX idx_function_code ON function_info USING btree (function_code);
CREATE INDEX idx_service_kbn_code ON function_info USING btree (service_kbn_code);

-- 案件マスター
CREATE TABLE project (
    ticket_number varchar(50) NOT NULL,
    project_name varchar(100) NOT NULL,
    description text NULL,
    service_kbn_code varchar(50) NULL,
    status varchar(50) NOT NULL DEFAULT '未着手',
    priority varchar(50) NOT NULL DEFAULT '中',
    progress_rate int4 DEFAULT 0 NULL,
    is_active bool DEFAULT true NULL,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT project_pkey PRIMARY KEY (ticket_number)
);

COMMENT ON TABLE project IS '案件マスター';
COMMENT ON COLUMN project.ticket_number IS 'チケット番号';
COMMENT ON COLUMN project.project_name IS '案件名';
COMMENT ON COLUMN project.description IS '説明';
COMMENT ON COLUMN project.service_kbn_code IS 'サービス区分コード';
COMMENT ON COLUMN project.status IS 'ステータス';
COMMENT ON COLUMN project.priority IS '優先度';
COMMENT ON COLUMN project.progress_rate IS '進捗率';
COMMENT ON COLUMN project.is_active IS '有効フラグ';
COMMENT ON COLUMN project.created_at IS '作成日時';
COMMENT ON COLUMN project.updated_at IS '更新日時';

CREATE INDEX idx_project_name ON project USING btree (project_name);
CREATE INDEX idx_project_service_kbn ON project USING btree (service_kbn_code);

-- 機能情報
CREATE TABLE feature (
    feature_id serial4 NOT NULL,
    project_id int4 NOT NULL,
    feature_name varchar(200) NOT NULL,
    description text NOT NULL,
    progress_rate numeric(5,2) NOT NULL,
    status varchar(50) NOT NULL,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT feature_pkey PRIMARY KEY (feature_id)
);

COMMENT ON TABLE feature IS '機能情報';
COMMENT ON COLUMN feature.feature_id IS '機能ID';
COMMENT ON COLUMN feature.project_id IS '案件ID';
COMMENT ON COLUMN feature.feature_name IS '機能名';
COMMENT ON COLUMN feature.description IS '説明';
COMMENT ON COLUMN feature.progress_rate IS '進捗率';
COMMENT ON COLUMN feature.status IS 'ステータス';
COMMENT ON COLUMN feature.created_at IS '作成日時';
COMMENT ON COLUMN feature.updated_at IS '更新日時';

CREATE INDEX idx_feature_project ON feature USING btree (project_id);

-- タスク情報
CREATE TABLE task (
    task_id serial4 NOT NULL,
    feature_id int4 NOT NULL,
    category_type_id int4 NOT NULL,
    category_id int4 NOT NULL,
    task_name varchar(200) NOT NULL,
    description text NOT NULL,
    planned_hours numeric(7,2) NOT NULL,
    actual_hours numeric(7,2) NOT NULL,
    progress_rate numeric(5,2) NOT NULL,
    status varchar(50) NOT NULL,
    start_date date NULL,
    end_date date NULL,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT task_pkey PRIMARY KEY (task_id)
);

COMMENT ON TABLE task IS 'タスク情報';
COMMENT ON COLUMN task.task_id IS 'タスクID';
COMMENT ON COLUMN task.feature_id IS '機能ID';
COMMENT ON COLUMN task.category_type_id IS '区分種別ID';
COMMENT ON COLUMN task.category_id IS '区分ID';
COMMENT ON COLUMN task.task_name IS 'タスク名';
COMMENT ON COLUMN task.description IS '説明';
COMMENT ON COLUMN task.planned_hours IS '予定工数';
COMMENT ON COLUMN task.actual_hours IS '実績工数';
COMMENT ON COLUMN task.progress_rate IS '進捗率';
COMMENT ON COLUMN task.status IS 'ステータス';
COMMENT ON COLUMN task.start_date IS '開始日';
COMMENT ON COLUMN task.end_date IS '終了日';
COMMENT ON COLUMN task.created_at IS '作成日時';
COMMENT ON COLUMN task.updated_at IS '更新日時';

CREATE INDEX idx_task_feature ON task USING btree (feature_id);
CREATE INDEX idx_task_category ON task USING btree (category_id);

-- タスク担当者
CREATE TABLE task_staff (
    task_id int4 NOT NULL,
    staff_id int4 NOT NULL,
    assigned_date date NOT NULL,
    end_date date NULL,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT task_staff_pkey PRIMARY KEY (task_id, staff_id)
);

COMMENT ON TABLE task_staff IS 'タスク担当者';
COMMENT ON COLUMN task_staff.task_id IS 'タスクID';
COMMENT ON COLUMN task_staff.staff_id IS '担当者ID';
COMMENT ON COLUMN task_staff.assigned_date IS 'アサイン日';
COMMENT ON COLUMN task_staff.end_date IS '終了日';
COMMENT ON COLUMN task_staff.created_at IS '作成日時';
COMMENT ON COLUMN task_staff.updated_at IS '更新日時';

CREATE INDEX idx_task_staff_staff ON task_staff USING btree (staff_id);

-- 日別実績工数
CREATE TABLE daily_work (
    daily_work_id serial4 NOT NULL,
    task_id int4 NOT NULL,
    staff_id int4 NOT NULL,
    work_date date NOT NULL,
    actual_hours numeric(5,2) NOT NULL,
    comment text NULL,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT daily_work_pkey PRIMARY KEY (daily_work_id)
);

COMMENT ON TABLE daily_work IS '日別実績工数';
COMMENT ON COLUMN daily_work.daily_work_id IS '実績工数ID';
COMMENT ON COLUMN daily_work.task_id IS 'タスクID';
COMMENT ON COLUMN daily_work.staff_id IS '担当者ID';
COMMENT ON COLUMN daily_work.work_date IS '作業日';
COMMENT ON COLUMN daily_work.actual_hours IS '実績工数';
COMMENT ON COLUMN daily_work.comment IS 'コメント';
COMMENT ON COLUMN daily_work.created_at IS '作成日時';
COMMENT ON COLUMN daily_work.updated_at IS '更新日時';

CREATE INDEX idx_daily_work_task ON daily_work USING btree (task_id);
CREATE INDEX idx_daily_work_staff ON daily_work USING btree (staff_id);
CREATE INDEX idx_daily_work_date ON daily_work USING btree (work_date);

-- 案件対象機能情報
CREATE TABLE project_function_info (
    service_kbn_code varchar(50) NOT NULL,
    ticket_number varchar(50) NOT NULL,
    function_code varchar(50) NOT NULL,
    task_kbn_code varchar(50) NOT NULL,
    staff_email varchar(256) NOT NULL,
    plan_start_date date NULL,
    plan_end_date date NULL,
    plan_man_hour numeric(7,2) NULL,
    actual_start_date date NULL,
    actual_end_date date NULL,
    actual_man_hour numeric(7,2) NULL,
    progress_rate int4 DEFAULT 0 NULL,
    delay_days int4 DEFAULT 0 NULL,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT project_function_info_pkey PRIMARY KEY (service_kbn_code, ticket_number, function_code, task_kbn_code, staff_email)
);

COMMENT ON TABLE project_function_info IS '案件対象機能情報';
COMMENT ON COLUMN project_function_info.service_kbn_code IS 'サービス区分コード';
COMMENT ON COLUMN project_function_info.ticket_number IS 'チケット番号';
COMMENT ON COLUMN project_function_info.function_code IS '機能コード';
COMMENT ON COLUMN project_function_info.task_kbn_code IS 'タスク区分コード';
COMMENT ON COLUMN project_function_info.staff_email IS '担当者メールアドレス';
COMMENT ON COLUMN project_function_info.plan_start_date IS '予定開始日';
COMMENT ON COLUMN project_function_info.plan_end_date IS '予定終了日';
COMMENT ON COLUMN project_function_info.plan_man_hour IS '予定工数';
COMMENT ON COLUMN project_function_info.actual_start_date IS '実績開始日';
COMMENT ON COLUMN project_function_info.actual_end_date IS '実績終了日';
COMMENT ON COLUMN project_function_info.actual_man_hour IS '実績工数';
COMMENT ON COLUMN project_function_info.progress_rate IS '進捗率';
COMMENT ON COLUMN project_function_info.delay_days IS '遅延日数';
COMMENT ON COLUMN project_function_info.created_at IS '作成日時';
COMMENT ON COLUMN project_function_info.updated_at IS '更新日時';

CREATE INDEX idx_project_function_ticket ON project_function_info USING btree (ticket_number);
CREATE INDEX idx_project_function_staff ON project_function_info USING btree (staff_email);
CREATE INDEX idx_project_function_composite ON project_function_info USING btree (service_kbn_code, function_code); 