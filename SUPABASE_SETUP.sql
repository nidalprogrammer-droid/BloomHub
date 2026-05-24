-- BloomHub Setup Script: Supabase PostgreSQL & pg_cron --

-- 1. Table Definitions

CREATE TABLE users (
    user_id TEXT PRIMARY KEY,
    user_name TEXT NOT NULL,
    active_status TEXT DEFAULT 'offline',
    current_milestone_completed BOOLEAN DEFAULT false,
    is_banned BOOLEAN DEFAULT false
);

CREATE TABLE friendships (
    friendship_id SERIAL PRIMARY KEY,
    user_id_1 TEXT REFERENCES users(user_id) ON DELETE CASCADE,
    user_id_2 TEXT REFERENCES users(user_id) ON DELETE CASCADE,
    shared_garden_level INTEGER DEFAULT 0
);

CREATE TABLE tasks (
    task_id SERIAL PRIMARY KEY,
    user_id TEXT REFERENCES users(user_id) ON DELETE CASCADE,
    task_name TEXT NOT NULL,
    is_urgent BOOLEAN DEFAULT false,
    is_completed BOOLEAN DEFAULT false,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    deadline TIMESTAMPTZ
);

CREATE TABLE sub_tasks (
    sub_task_id SERIAL PRIMARY KEY,
    parent_task_id INTEGER REFERENCES tasks(task_id) ON DELETE CASCADE,
    sub_task_name TEXT NOT NULL,
    is_completed BOOLEAN DEFAULT false,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

CREATE TABLE encouragement_leaves (
    leaf_id SERIAL PRIMARY KEY,
    text_content TEXT NOT NULL,
    is_approved BOOLEAN DEFAULT true,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

CREATE TABLE daily_moods (
    mood_id SERIAL PRIMARY KEY,
    user_id TEXT REFERENCES users(user_id) ON DELETE CASCADE,
    voice_note_transcript TEXT,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

-- 2. Row Level Security (RLS)
-- If a user is banned, they cannot read, write, or access any data.

ALTER TABLE users ENABLE ROW LEVEL SECURITY;
ALTER TABLE friendships ENABLE ROW LEVEL SECURITY;
ALTER TABLE tasks ENABLE ROW LEVEL SECURITY;
ALTER TABLE sub_tasks ENABLE ROW LEVEL SECURITY;
ALTER TABLE encouragement_leaves ENABLE ROW LEVEL SECURITY;
ALTER TABLE daily_moods ENABLE ROW LEVEL SECURITY;

-- Block Cloud Read/Write Constraints for Banned Users
-- (Assumption: auth.uid() can map to user_id for strict matching, using generic matching here)
CREATE OR REPLACE FUNCTION is_user_banned(current_user_id TEXT)
RETURNS BOOLEAN AS $$
  SELECT is_banned FROM users WHERE user_id = current_user_id;
$$ LANGUAGE sql SECURITY DEFINER;

CREATE POLICY "Ban Policy: Prevent Read for Banned Users"
ON users FOR SELECT
USING (NOT is_user_banned(user_id));

CREATE POLICY "Ban Policy: Prevent Write for Banned Users"
ON users FOR ALL
USING (NOT is_user_banned(user_id));

-- (Apply similar rules to tasks, friendships, daily_moods, etc. depending on app logic)

-- 3. 7-Day Automatic Data Purge Engine (Data Retention)
-- Requires pg_cron extension: CREATE EXTENSION IF NOT EXISTS pg_cron;

-- Delete tasks older than 7 days
SELECT cron.schedule(
    'purge_tasks_7_days',
    '0 0 * * *', -- At midnight every day
    $$ DELETE FROM tasks WHERE created_at < NOW() - INTERVAL '7 days' $$
);

-- Delete encouragement leaves older than 7 days
SELECT cron.schedule(
    'purge_leaves_7_days',
    '0 0 * * *',
    $$ DELETE FROM encouragement_leaves WHERE created_at < NOW() - INTERVAL '7 days' $$
);
