-- taskテーブルの外部キー制約を削除
ALTER TABLE IF EXISTS task
    DROP CONSTRAINT IF EXISTS task_category_id_fkey,
    DROP CONSTRAINT IF EXISTS task_project_id_fkey,
    DROP CONSTRAINT IF EXISTS task_parent_task_id_fkey,
    DROP CONSTRAINT IF EXISTS task_category_type_id_fkey,
    DROP CONSTRAINT IF EXISTS task_category_fkey;

-- project_categoryテーブルの外部キー制約を削除
ALTER TABLE IF EXISTS project_category
    DROP CONSTRAINT IF EXISTS project_category_project_id_fkey,
    DROP CONSTRAINT IF EXISTS project_category_category_id_fkey,
    DROP CONSTRAINT IF EXISTS project_category_category_type_id_fkey,
    DROP CONSTRAINT IF EXISTS project_category_fkey;

-- function_infoテーブルの外部キー制約を削除
ALTER TABLE IF EXISTS function_info
    DROP CONSTRAINT IF EXISTS function_info_category_id_fkey,
    DROP CONSTRAINT IF EXISTS function_info_category_fkey;

-- categoryテーブルの外部キー制約を削除
ALTER TABLE IF EXISTS category
    DROP CONSTRAINT IF EXISTS category_category_type_id_fkey;

-- project_memberテーブルの外部キー制約を削除
ALTER TABLE IF EXISTS project_member
    DROP CONSTRAINT IF EXISTS project_member_project_id_fkey,
    DROP CONSTRAINT IF EXISTS project_member_user_id_fkey;

-- project_taskテーブルの外部キー制約を削除
ALTER TABLE IF EXISTS project_task
    DROP CONSTRAINT IF EXISTS project_task_project_id_fkey,
    DROP CONSTRAINT IF EXISTS project_task_task_id_fkey;

-- その他の可能性のある外部キー制約も削除
DO $$ 
DECLARE
    r RECORD;
BEGIN
    FOR r IN (SELECT DISTINCT tc.table_name, tc.constraint_name
              FROM information_schema.table_constraints tc
              JOIN information_schema.constraint_column_usage ccu 
              ON tc.constraint_name = ccu.constraint_name
              WHERE tc.constraint_type = 'FOREIGN KEY') 
    LOOP
        EXECUTE 'ALTER TABLE IF EXISTS ' || quote_ident(r.table_name) || 
                ' DROP CONSTRAINT IF EXISTS ' || quote_ident(r.constraint_name);
    END LOOP;
END $$; 