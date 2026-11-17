package com.example.loldetector.dto;

import java.time.LocalDateTime;

public class SecurityAlertDto {

    private Long id;
    private Long analysisId;
    private String eventId;
    private String ruleId;
    private String ruleName;
    private String severity;
    private String description;
    private String mitreTechniqueId;
    private String mitreTechniqueName;
    private LocalDateTime detectedAt;
    private String rawCommandLine;
    private String processName;
    private String parentProcessName;
    private String userAccount;
    private String investigationStatus;

    public SecurityAlertDto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getAnalysisId() { return analysisId; }
    public void setAnalysisId(Long analysisId) { this.analysisId = analysisId; }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getRuleId() { return ruleId; }
    public void setRuleId(String ruleId) { this.ruleId = ruleId; }

    public String getRuleName() { return ruleName; }
    public void setRuleName(String ruleName) { this.ruleName = ruleName; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getMitreTechniqueId() { return mitreTechniqueId; }
    public void setMitreTechniqueId(String mitreTechniqueId) { this.mitreTechniqueId = mitreTechniqueId; }

    public String getMitreTechniqueName() { return mitreTechniqueName; }
    public void setMitreTechniqueName(String mitreTechniqueName) { this.mitreTechniqueName = mitreTechniqueName; }

    public LocalDateTime getDetectedAt() { return detectedAt; }
    public void setDetectedAt(LocalDateTime detectedAt) { this.detectedAt = detectedAt; }

    public String getRawCommandLine() { return rawCommandLine; }
    public void setRawCommandLine(String rawCommandLine) { this.rawCommandLine = rawCommandLine; }

    public String getProcessName() { return processName; }
    public void setProcessName(String processName) { this.processName = processName; }

    public String getParentProcessName() { return parentProcessName; }
    public void setParentProcessName(String parentProcessName) { this.parentProcessName = parentProcessName; }

    public String getUserAccount() { return userAccount; }
    public void setUserAccount(String userAccount) { this.userAccount = userAccount; }

    public String getInvestigationStatus() { return investigationStatus; }
    public void setInvestigationStatus(String investigationStatus) { this.investigationStatus = investigationStatus; }
}
