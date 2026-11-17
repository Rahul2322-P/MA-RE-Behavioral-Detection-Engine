CREATE DATABASE IF NOT EXISTS lol_detector_db;
USE lol_detector_db;

CREATE TABLE IF NOT EXISTS analysis_runs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    file_name VARCHAR(255) NOT NULL,
    started_at DATETIME(6) NOT NULL,
    completed_at DATETIME(6),
    total_events INT NOT NULL DEFAULT 0,
    rejected_events INT NOT NULL DEFAULT 0,
    alert_count INT NOT NULL DEFAULT 0,
    status VARCHAR(50) NOT NULL DEFAULT 'COMPLETED',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS security_alerts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    analysis_id BIGINT NOT NULL,
    event_id VARCHAR(100) NOT NULL,
    rule_id VARCHAR(50) NOT NULL,
    rule_name VARCHAR(150) NOT NULL,
    severity VARCHAR(20) NOT NULL,
    description TEXT NOT NULL,
    mitre_technique_id VARCHAR(50),
    mitre_technique_name VARCHAR(150),
    detected_at DATETIME(6) NOT NULL,
    raw_command_line TEXT,
    process_name VARCHAR(100),
    parent_process_name VARCHAR(100),
    user_account VARCHAR(100),
    investigation_status VARCHAR(30) DEFAULT 'NEW',
    CONSTRAINT fk_alerts_analysis FOREIGN KEY (analysis_id) REFERENCES analysis_runs(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX idx_alerts_analysis_id ON security_alerts(analysis_id);
CREATE INDEX idx_alerts_severity ON security_alerts(severity);
