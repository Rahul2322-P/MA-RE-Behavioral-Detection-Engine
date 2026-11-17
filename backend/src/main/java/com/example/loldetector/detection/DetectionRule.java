package com.example.loldetector.detection;

import com.example.loldetector.dto.LogEventDto;

public abstract class DetectionRule {

    private final String ruleId;
    private final String ruleName;
    private final String description;
    private final String targetProcess;
    private final String severity; // LOW, MEDIUM, HIGH, CRITICAL
    private final String mitreTechniqueId;
    private final String mitreTechniqueName;
    private final String explanation;

    public DetectionRule(String ruleId, String ruleName, String description, String targetProcess, 
                         String severity, String mitreTechniqueId, String mitreTechniqueName, String explanation) {
        this.ruleId = ruleId;
        this.ruleName = ruleName;
        this.description = description;
        this.targetProcess = targetProcess;
        this.severity = severity;
        this.mitreTechniqueId = mitreTechniqueId;
        this.mitreTechniqueName = mitreTechniqueName;
        this.explanation = explanation;
    }

    public abstract boolean matches(LogEventDto event);

    public String getRuleId() { return ruleId; }
    public String getRuleName() { return ruleName; }
    public String getDescription() { return description; }
    public String getTargetProcess() { return targetProcess; }
    public String getSeverity() { return severity; }
    public String getMitreTechniqueId() { return mitreTechniqueId; }
    public String getMitreTechniqueName() { return mitreTechniqueName; }
    public String getExplanation() { return explanation; }
}
