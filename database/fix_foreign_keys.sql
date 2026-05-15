USE ideaforge_platform;

-- Use this only if earlier tests left these columns nullable.
ALTER TABLE skills MODIFY COLUMN profile_id BIGINT NOT NULL;
ALTER TABLE interests MODIFY COLUMN profile_id BIGINT NOT NULL;
ALTER TABLE required_roles MODIFY COLUMN idea_id BIGINT NOT NULL;
ALTER TABLE team_members MODIFY COLUMN team_id BIGINT NOT NULL;
ALTER TABLE messages MODIFY COLUMN conversation_id BIGINT NOT NULL;
