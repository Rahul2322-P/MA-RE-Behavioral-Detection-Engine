package com.example.loldetector.detection;

import com.example.loldetector.dto.LogEventDto;
import com.example.loldetector.entity.SecurityAlert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BehavioralDetectionEngine {

    private final List<DetectionRule> rules;

    @Autowired
    public BehavioralDetectionEngine(List<DetectionRule> rules) {
        this.rules = rules;
    }

    public List<SecurityAlert> analyzeEvents(List<LogEventDto> events) {
        List<SecurityAlert> generatedAlerts = new ArrayList<>();

        if (events == null || events.isEmpty()) {
            return generatedAlerts;
        }

        for (LogEventDto event : events) {
            for (DetectionRule rule : rules) {
                if (rule.matches(event)) {
                    SecurityAlert alert = new SecurityAlert(
                            event.getEventId(),
                            rule.getRuleId(),
                            rule.getRuleName(),
                            rule.getSeverity(),
                            rule.getDescription(),
                            rule.getMitreTechniqueId(),
                            rule.getMitreTechniqueName(),
                            event.getTimestamp() != null ? event.getTimestamp() : LocalDateTime.now(),
                            event.getCommandLine(),
                            event.getProcessName(),
                            event.getParentProcessName(),
                            event.getUser()
                    );
                    generatedAlerts.add(alert);
                }
            }
        }

        return generatedAlerts;
    }

    public List<DetectionRule> getActiveRules() {
        return rules;
    }
}
