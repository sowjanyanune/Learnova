CREATE DATABASE IF NOT EXISTS learnova_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE learnova_db;

CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    total_xp INT NOT NULL DEFAULT 0,
    player_level INT NOT NULL DEFAULT 1,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_users_xp CHECK (total_xp >= 0),
    CONSTRAINT chk_users_level CHECK (player_level >= 1)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS admins (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS missions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    category VARCHAR(30) NOT NULL,
    difficulty VARCHAR(20) NOT NULL,
    option_a VARCHAR(255) NOT NULL,
    option_b VARCHAR(255) NOT NULL,
    option_c VARCHAR(255) NOT NULL,
    option_d VARCHAR(255) NOT NULL,
    correct_answer VARCHAR(255) NOT NULL,
    xp_reward INT NOT NULL DEFAULT 50,
    created_by INT NOT NULL,
    CONSTRAINT fk_missions_admin FOREIGN KEY (created_by) REFERENCES admins(id) ON DELETE RESTRICT,
    CONSTRAINT chk_missions_xp CHECK (xp_reward BETWEEN 10 AND 500)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS mission_submissions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    mission_id INT NOT NULL,
    answer VARCHAR(255) NOT NULL,
    is_correct BOOLEAN NOT NULL DEFAULT FALSE,
    completed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_submissions_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_submissions_mission FOREIGN KEY (mission_id) REFERENCES missions(id) ON DELETE RESTRICT,
    CONSTRAINT uq_submission_one_attempt UNIQUE (user_id, mission_id)
) ENGINE=InnoDB;
