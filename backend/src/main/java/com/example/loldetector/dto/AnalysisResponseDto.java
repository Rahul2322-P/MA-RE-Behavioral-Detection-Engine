package com.example.loldetector.dto;

import java.time.LocalDateTime;
import java.util.List;

public class AnalysisResponseDto {

    private Long analysisId;
    private String fileName;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private Integer totalEvents;
    private Integer rejectedEvents;
    private Integer alertCount;
    private String status;
    private List<SecurityAlertDto> alerts;

    public AnalysisResponseDto() {}

    public Long getAnalysisId() { return analysisId; }
    public void setAnalysisId(Long analysisId) { this.analysisId = analysisId; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public LocalDateTime getStartedAt() { return startedAt; }
    public void setStartedAt(LocalDateTime startedAt) { this.startedAt = startedAt; }

    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }

    public Integer getTotalEvents() { return totalEvents; }
    public void setTotalEvents(Integer totalEvents) { this.totalEvents = totalEvents; }

    public Integer getRejectedEvents() { return rejectedEvents; }
    public void setRejectedEvents(Integer rejectedEvents) { this.rejectedEvents = rejectedEvents; }

    public Integer getAlertCount() { return alertCount; }
    public void setAlertCount(Integer alertCount) { this.alertCount = alertCount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<SecurityAlertDto> getAlerts() { return alerts; }
    public void setAlerts(List<SecurityAlertDto> alerts) { this.alerts = alerts; }
}
