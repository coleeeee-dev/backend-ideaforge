CREATE DATABASE IF NOT EXISTS ideaforge_platform
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE ideaforge_platform;

CREATE TABLE IF NOT EXISTS accounts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(120) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('USER', 'ADMIN') NOT NULL DEFAULT 'USER',
    status ENUM('ACTIVE', 'INACTIVE', 'BLOCKED') NOT NULL DEFAULT 'ACTIVE',
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    CONSTRAINT uq_accounts_email UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS profiles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_id BIGINT NOT NULL,
    first_name VARCHAR(80) NOT NULL,
    last_name VARCHAR(80) NOT NULL,
    headline VARCHAR(120),
    bio TEXT,
    avatar_url VARCHAR(255),
    experience_level ENUM('BEGINNER', 'JUNIOR', 'INTERMEDIATE', 'SENIOR') DEFAULT 'BEGINNER',
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    CONSTRAINT uq_profiles_account_id UNIQUE (account_id),
    CONSTRAINT fk_profiles_accounts FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS skills (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    profile_id BIGINT NOT NULL,
    name VARCHAR(80) NOT NULL,
    proficiency_level ENUM('BASIC', 'INTERMEDIATE', 'ADVANCED') DEFAULT 'BASIC',
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    CONSTRAINT fk_skills_profiles FOREIGN KEY (profile_id) REFERENCES profiles(id) ON DELETE CASCADE,
    CONSTRAINT uq_skills_profile_name UNIQUE (profile_id, name)
);

CREATE TABLE IF NOT EXISTS interests (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    profile_id BIGINT NOT NULL,
    name VARCHAR(80) NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    CONSTRAINT fk_interests_profiles FOREIGN KEY (profile_id) REFERENCES profiles(id) ON DELETE CASCADE,
    CONSTRAINT uq_interests_profile_name UNIQUE (profile_id, name)
);

CREATE TABLE IF NOT EXISTS ideas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    creator_profile_id BIGINT NOT NULL,
    title VARCHAR(120) NOT NULL,
    short_description VARCHAR(255),
    description TEXT NOT NULL,
    problem TEXT,
    solution TEXT,
    category ENUM('TECHNOLOGY','EDUCATION','HEALTH','FINANCE','SOCIAL','CREATIVE','BUSINESS','OTHER') DEFAULT 'OTHER',
    status ENUM('DRAFT', 'OPEN', 'IN_PROGRESS', 'CLOSED', 'INACTIVE') NOT NULL DEFAULT 'DRAFT',
    stage ENUM('IDEA', 'VALIDATION', 'PROTOTYPE', 'MVP', 'LAUNCHED') NOT NULL DEFAULT 'IDEA',
    collaboration_mode ENUM('REMOTE', 'HYBRID', 'IN_PERSON') DEFAULT 'REMOTE',
    expected_commitment ENUM('LOW', 'MEDIUM', 'HIGH') DEFAULT 'MEDIUM',
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    CONSTRAINT fk_ideas_creator_profiles FOREIGN KEY (creator_profile_id) REFERENCES profiles(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS required_roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    idea_id BIGINT NOT NULL,
    role_name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    quantity INT NOT NULL DEFAULT 1,
    required_experience_level ENUM('BEGINNER', 'JUNIOR', 'INTERMEDIATE', 'SENIOR') DEFAULT 'BEGINNER',
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    CONSTRAINT fk_required_roles_ideas FOREIGN KEY (idea_id) REFERENCES ideas(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS saved_ideas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    profile_id BIGINT NOT NULL,
    idea_id BIGINT NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    CONSTRAINT fk_saved_ideas_profiles FOREIGN KEY (profile_id) REFERENCES profiles(id) ON DELETE CASCADE,
    CONSTRAINT fk_saved_ideas_ideas FOREIGN KEY (idea_id) REFERENCES ideas(id) ON DELETE CASCADE,
    CONSTRAINT uq_saved_ideas_profile_idea UNIQUE (profile_id, idea_id)
);

CREATE TABLE IF NOT EXISTS project_applications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    idea_id BIGINT NOT NULL,
    applicant_profile_id BIGINT NOT NULL,
    requested_role VARCHAR(100),
    message TEXT,
    status ENUM('PENDING', 'ACCEPTED', 'REJECTED', 'CANCELLED') NOT NULL DEFAULT 'PENDING',
    decided_at DATETIME(6),
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    CONSTRAINT fk_project_applications_ideas FOREIGN KEY (idea_id) REFERENCES ideas(id) ON DELETE CASCADE,
    CONSTRAINT fk_project_applications_profiles FOREIGN KEY (applicant_profile_id) REFERENCES profiles(id) ON DELETE CASCADE,
    CONSTRAINT uq_project_applications_idea_applicant UNIQUE (idea_id, applicant_profile_id)
);

CREATE TABLE IF NOT EXISTS teams (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    idea_id BIGINT NOT NULL,
    name VARCHAR(120) NOT NULL,
    status ENUM('FORMING', 'ACTIVE', 'CLOSED') NOT NULL DEFAULT 'FORMING',
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    CONSTRAINT uq_teams_idea_id UNIQUE (idea_id),
    CONSTRAINT fk_teams_ideas FOREIGN KEY (idea_id) REFERENCES ideas(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS team_members (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    team_id BIGINT NOT NULL,
    profile_id BIGINT NOT NULL,
    role_name VARCHAR(100) NOT NULL,
    member_status ENUM('ACTIVE', 'REMOVED') NOT NULL DEFAULT 'ACTIVE',
    joined_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    CONSTRAINT fk_team_members_teams FOREIGN KEY (team_id) REFERENCES teams(id) ON DELETE CASCADE,
    CONSTRAINT fk_team_members_profiles FOREIGN KEY (profile_id) REFERENCES profiles(id) ON DELETE CASCADE,
    CONSTRAINT uq_team_members_team_profile UNIQUE (team_id, profile_id)
);

CREATE TABLE IF NOT EXISTS conversations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    idea_id BIGINT NOT NULL,
    project_application_id BIGINT,
    creator_profile_id BIGINT NOT NULL,
    applicant_profile_id BIGINT NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    CONSTRAINT fk_conversations_ideas FOREIGN KEY (idea_id) REFERENCES ideas(id) ON DELETE CASCADE,
    CONSTRAINT fk_conversations_project_applications FOREIGN KEY (project_application_id) REFERENCES project_applications(id) ON DELETE SET NULL,
    CONSTRAINT fk_conversations_creator_profiles FOREIGN KEY (creator_profile_id) REFERENCES profiles(id) ON DELETE CASCADE,
    CONSTRAINT fk_conversations_applicant_profiles FOREIGN KEY (applicant_profile_id) REFERENCES profiles(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS messages (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    conversation_id BIGINT NOT NULL,
    sender_profile_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    sent_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    CONSTRAINT fk_messages_conversations FOREIGN KEY (conversation_id) REFERENCES conversations(id) ON DELETE CASCADE,
    CONSTRAINT fk_messages_sender_profiles FOREIGN KEY (sender_profile_id) REFERENCES profiles(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS notifications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    recipient_profile_id BIGINT NOT NULL,
    type ENUM('NEW_APPLICATION','APPLICATION_ACCEPTED','APPLICATION_REJECTED','NEW_MESSAGE','REPORT_RESOLVED') NOT NULL,
    title VARCHAR(120) NOT NULL,
    body VARCHAR(255) NOT NULL,
    read_at DATETIME(6),
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    CONSTRAINT fk_notifications_profiles FOREIGN KEY (recipient_profile_id) REFERENCES profiles(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS reports (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    reporter_profile_id BIGINT NOT NULL,
    target_type ENUM('IDEA', 'PROFILE') NOT NULL,
    target_id BIGINT NOT NULL,
    reason ENUM('SPAM','INAPPROPRIATE_CONTENT','FAKE_PROFILE','SCAM','OTHER') NOT NULL,
    description TEXT,
    status ENUM('PENDING', 'RESOLVED', 'DISMISSED') NOT NULL DEFAULT 'PENDING',
    decision ENUM('NO_ACTION','HIDE_CONTENT','WARN_USER','BLOCK_USER','DELETE_CONTENT'),
    resolved_by_account_id BIGINT,
    resolved_at DATETIME(6),
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    CONSTRAINT fk_reports_reporter_profiles FOREIGN KEY (reporter_profile_id) REFERENCES profiles(id) ON DELETE CASCADE,
    CONSTRAINT fk_reports_resolver_accounts FOREIGN KEY (resolved_by_account_id) REFERENCES accounts(id) ON DELETE SET NULL
);
