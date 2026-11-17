package com.example.loldetector.dto;

import java.util.List;
import java.util.Map;

public class DashboardSummaryDto {

    private Long totalAnalyses;
    private Long totalEventsProcessed;
    private Long totalAlertsGenerated;
    private Map<String, Long> alertsBySeverity;
    private List<AnalysisRunDto> recentAnalyses;
    private List<SecurityAlertDto> recentAlerts;

    public DashboardSummaryDto() {}

    public DashboardSummaryDto(Long totalAnalyses, Long totalEventsProcessed, Long totalAlertsGenerated, 
                               Map<String, Long> alertsBySeverity, List<AnalysisRunDto> recentAnalyses, 
                               List<SecurityAlertDto> recentAlerts) {
        this.totalAnalyses = totalAnalyses;
        this.totalEventsProcessed = totalEventsProcessed;
        this.totalAlertsGenerated = totalAlertsGenerated;
        this.alertsBySeverity = alertsBySeverity;
        this.recentAnalyses = recentAnalyses;
        this.recentAlerts = recentAlerts;
    }

    public Long getTotalAnalyses() { return totalAnalyses; }
    public void setTotalAnalyses(Long totalAnalyses) { this.totalAnalyses = totalAnalyses; }

    public Long getTotalEventsProcessed() { return totalEventsProcessed; }
    public void setTotalEventsProcessed(Long totalEventsProcessed) { this.totalEventsProcessed = totalEventsProcessed; }

    public Long getTotalAlertsGenerated() { return totalAlertsGenerated; }
    public void setTotalAlertsGenerated(Long totalAlertsGenerated) { this.totalAlertsGenerated = totalAlertsGenerated; }

    public Map<String, Long> getAlertsBySeverity() { return alertsBySeverity; }
    public void setAlertsBySeverity(Map<String, Long> alertsBySeverity) { this.alertsBySeverity = alertsBySeverity; }

    public List<AnalysisRunDto> getRecentAnalyses() { return recentAnalyses; }
    public void setRecentAnalyses(List<AnalysisRunDto> recentAnalyses) { this.recentAnalyses = recentAnalyses; }

    public List<SecurityAlertDto> getRecentAlerts() { return recentAlerts; }
    public void setRecentAlerts(List<SecurityAlertDto> recentAlerts) { this.recentAlerts = recentAlerts; }
}
