-- 担当者マスター
CREATE TABLE staff (
    staff_id SERIAL PRIMARY KEY,
    staff_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    department VARCHAR(100) NOT NULL,
    is_active BOOLEAN DEFAULT true
);

-- 区分種別マスター
CREATE TABLE category_type (
    category_type_id SERIAL PRIMARY KEY,
    category_type_name VARCHAR(100) NOT NULL,
    description TEXT,
    display_order INTEGER,
    is_active BOOLEAN DEFAULT true
);

-- 区分マスター
CREATE TABLE category (
    category_id SERIAL PRIMARY KEY,
    category_type_id INTEGER NOT NULL REFERENCES category_type(category_type_id),
    category_name VARCHAR(100) NOT NULL,
    description TEXT,
    display_order INTEGER,
    is_active BOOLEAN DEFAULT true
);

-- 案件情報
CREATE TABLE project (
    project_id SERIAL PRIMARY KEY,
    service_type VARCHAR(100) NOT NULL,
    project_name VARCHAR(200) NOT NULL,
    status VARCHAR(50) NOT NULL,
    priority VARCHAR(50) NOT NULL,
    staff_id INTEGER NOT NULL REFERENCES staff(staff_id),
    progress_rate NUMERIC(5,2) NOT NULL,
    dev_scheduled_date DATE,
    dev_actual_date DATE,
    st_scheduled_date DATE,
    st_actual_date DATE,
    prod_scheduled_date DATE,
    prod_actual_date DATE
);

-- 機能情報
CREATE TABLE feature (
    feature_id SERIAL PRIMARY KEY,
    project_id INTEGER NOT NULL REFERENCES project(project_id),
    feature_name VARCHAR(200) NOT NULL,
    description TEXT NOT NULL,
    progress_rate NUMERIC(5,2) NOT NULL,
    status VARCHAR(50) NOT NULL
);

-- タスク情報
CREATE TABLE task (
    task_id SERIAL PRIMARY KEY,
    feature_id INTEGER NOT NULL REFERENCES feature(feature_id),
    category_type_id INTEGER NOT NULL REFERENCES category_type(category_type_id),
    category_id INTEGER NOT NULL REFERENCES category(category_id),
    task_name VARCHAR(200) NOT NULL,
    description TEXT NOT NULL,
    planned_hours NUMERIC(7,2) NOT NULL,
    actual_hours NUMERIC(7,2) NOT NULL,
    progress_rate NUMERIC(5,2) NOT NULL,
    status VARCHAR(50) NOT NULL,
    start_date DATE,
    end_date DATE
);

-- タスク担当者
CREATE TABLE task_staff (
    task_id INTEGER NOT NULL REFERENCES task(task_id),
    staff_id INTEGER NOT NULL REFERENCES staff(staff_id),
    assigned_date DATE NOT NULL,
    end_date DATE,
    PRIMARY KEY (task_id, staff_id)
);

-- 日別実績工数
CREATE TABLE daily_work (
    daily_work_id SERIAL PRIMARY KEY,
    task_id INTEGER NOT NULL,
    staff_id INTEGER NOT NULL,
    work_date DATE NOT NULL,
    actual_hours NUMERIC(5,2) NOT NULL,
    comment TEXT,
    FOREIGN KEY (task_id, staff_id) REFERENCES task_staff(task_id, staff_id)
); 