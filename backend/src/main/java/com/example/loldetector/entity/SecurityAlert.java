package com.example.loldetector.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "security_alerts")
public class SecurityAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "analysis_id", nullable = false)
    @JsonIgnore
    private AnalysisRun analysisRun;

    @Column(name = "event_id", nullable = false)
    private String eventId;

    @Column(name = "rule_id", nullable = false)
    private String ruleId;

    @Column(name = "rule_name", nullable = false)
    private String ruleName;

    @Column(name = "severity", nullable = false)
    private String severity;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "mitre_technique_id")
    private String mitreTechniqueId;

    @Column(name = "mitre_technique_name")
    private String mitreTechniqueName;

    @Column(name = "detected_at", nullable = false)
    private LocalDateTime detectedAt;

    @Column(name = "raw_command_line", columnDefinition = "TEXT")
    private String rawCommandLine;

    @Column(name = "process_name")
    private String processName;

    @Column(name = "parent_process_name")
    private String parentProcessName;

    @Column(name = "user_account")
    private String userAccount;

    @Column(name = "investigation_status")
    private String investigationStatus = "NEW";

    public SecurityAlert() {}

    public SecurityAlert(String eventId, String ruleId, String ruleName, String severity, 
                         String description, String mitreTechniqueId, String mitreTechniqueName, 
                         LocalDateTime detectedAt, String rawCommandLine, String processName, 
                         String parentProcessName, String userAccount) {
        this.eventId = eventId;
        this.ruleId = ruleId;
        this.ruleName = ruleName;
        this.severity = severity;
        this.description = description;
        this.mitreTechniqueId = mitreTechniqueId;
        this.mitreTechniqueName = mitreTechniqueName;
        this.detectedAt = detectedAt;
        this.rawCommandLine = rawCommandLine;
        this.processName = processName;
        this.parentProcessName = parentProcessName;
        this.userAccount = userAccount;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AnalysisRun getAnalysisRun() {
        return analysisRun;
    }

    public void setAnalysisRun(AnalysisRun analysisRun) {
        this.analysisRun = analysisRun;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMitreTechniqueId() {
        return mitreTechniqueId;
    }

    public void setMitreTechniqueId(String mitreTechniqueId) {
        this.mitreTechniqueId = mitreTechniqueId;
    }

    public String getMitreTechniqueName() {
        return mitreTechniqueName;
    }

    public void setMitreTechniqueName(String mitreTechniqueName) {
        this.mitreTechniqueName = mitreTechniqueName;
    }

    public LocalDateTime getDetectedAt() {
        return detectedAt;
    }

    public void setDetectedAt(LocalDateTime detectedAt) {
        this.detectedAt = detectedAt;
    }

    public String getRawCommandLine() {
        return rawCommandLine;
    }

    public void setRawCommandLine(String rawCommandLine) {
        this.rawCommandLine = rawCommandLine;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getParentProcessName() {
        return parentProcessName;
    }

    public void setParentProcessName(String parentProcessName) {
        this.parentProcessName = parentProcessName;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getInvestigationStatus() {
        return investigationStatus;
    }

    public void setInvestigationStatus(String investigationStatus) {
        this.investigationStatus = investigationStatus;
    }
}
