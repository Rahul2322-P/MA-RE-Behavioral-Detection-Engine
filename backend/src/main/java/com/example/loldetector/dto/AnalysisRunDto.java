package com.example.loldetector.dto;

import java.time.LocalDateTime;

public class AnalysisRunDto {

    private Long id;
    private String fileName;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private Integer totalEvents;
    private Integer rejectedEvents;
    private Integer alertCount;
    private String status;

    public AnalysisRunDto() {}

    public AnalysisRunDto(Long id, String fileName, LocalDateTime startedAt, LocalDateTime completedAt, 
                          Integer totalEvents, Integer rejectedEvents, Integer alertCount, String status) {
        this.id = id;
        this.fileName = fileName;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.totalEvents = totalEvents;
        this.rejectedEvents = rejectedEvents;
        this.alertCount = alertCount;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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
}
