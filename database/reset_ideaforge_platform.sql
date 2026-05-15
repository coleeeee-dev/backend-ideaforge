USE ideaforge_platform;

SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE messages;
TRUNCATE TABLE conversations;
TRUNCATE TABLE team_members;
TRUNCATE TABLE teams;
TRUNCATE TABLE project_applications;
TRUNCATE TABLE saved_ideas;
TRUNCATE TABLE required_roles;
TRUNCATE TABLE ideas;
TRUNCATE TABLE notifications;
TRUNCATE TABLE reports;
TRUNCATE TABLE skills;
TRUNCATE TABLE interests;
TRUNCATE TABLE profiles;
TRUNCATE TABLE accounts;

SET FOREIGN_KEY_CHECKS = 1;
