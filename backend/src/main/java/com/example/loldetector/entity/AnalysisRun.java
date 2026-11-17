package com.example.loldetector.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "analysis_runs")
public class AnalysisRun {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "total_events", nullable = false)
    private Integer totalEvents = 0;

    @Column(name = "rejected_events", nullable = false)
    private Integer rejectedEvents = 0;

    @Column(name = "alert_count", nullable = false)
    private Integer alertCount = 0;

    @Column(name = "status", nullable = false)
    private String status = "COMPLETED";

    @OneToMany(mappedBy = "analysisRun", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SecurityAlert> alerts = new ArrayList<>();

    public AnalysisRun() {}

    public AnalysisRun(String fileName, LocalDateTime startedAt, Integer totalEvents, Integer rejectedEvents) {
        this.fileName = fileName;
        this.startedAt = startedAt;
        this.totalEvents = totalEvents;
        this.rejectedEvents = rejectedEvents;
    }

    public void addAlert(SecurityAlert alert) {
        alerts.add(alert);
        alert.setAnalysisRun(this);
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public Integer getTotalEvents() {
        return totalEvents;
    }

    public void setTotalEvents(Integer totalEvents) {
        this.totalEvents = totalEvents;
    }

    public Integer getRejectedEvents() {
        return rejectedEvents;
    }

    public void setRejectedEvents(Integer rejectedEvents) {
        this.rejectedEvents = rejectedEvents;
    }

    public Integer getAlertCount() {
        return alertCount;
    }

    public void setAlertCount(Integer alertCount) {
        this.alertCount = alertCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SecurityAlert> getAlerts() {
        return alerts;
    }

    public void setAlerts(List<SecurityAlert> alerts) {
        this.alerts = alerts;
    }
}
