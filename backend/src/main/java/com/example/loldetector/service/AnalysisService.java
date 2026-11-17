package com.example.loldetector.service;

import com.example.loldetector.dto.*;
import com.example.loldetector.entity.AnalysisRun;
import com.example.loldetector.entity.SecurityAlert;
import com.example.loldetector.exception.ResourceNotFoundException;
import com.example.loldetector.repository.AnalysisRunRepository;
import com.example.loldetector.repository.SecurityAlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AnalysisService {

    private final AnalysisRunRepository analysisRunRepository;
    private final SecurityAlertRepository securityAlertRepository;

    @Autowired
    public AnalysisService(AnalysisRunRepository analysisRunRepository, 
                           SecurityAlertRepository securityAlertRepository) {
        this.analysisRunRepository = analysisRunRepository;
        this.securityAlertRepository = securityAlertRepository;
    }

    @Transactional
    public AnalysisRun createInitialRun(String fileName, int totalEvents, int rejectedEvents) {
        AnalysisRun run = new AnalysisRun(fileName, LocalDateTime.now(), totalEvents, rejectedEvents);
        run.setStatus("IN_PROGRESS");
        return analysisRunRepository.save(run);
    }

    @Transactional
    public AnalysisResponseDto completeAnalysisRun(AnalysisRun run, List<SecurityAlert> alerts) {
        run.setCompletedAt(LocalDateTime.now());
        run.setAlertCount(alerts.size());
        run.setStatus("COMPLETED");

        for (SecurityAlert alert : alerts) {
            run.addAlert(alert);
        }

        AnalysisRun savedRun = analysisRunRepository.save(run);
        return mapToResponseDto(savedRun);
    }

    @Transactional(readOnly = true)
    public List<AnalysisRunDto> getAllAnalysisRuns() {
        return analysisRunRepository.findAllByOrderByStartedAtDesc().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AnalysisResponseDto getAnalysisById(Long id) {
        AnalysisRun run = analysisRunRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Analysis run not found with ID: " + id));
        return mapToResponseDto(run);
    }

    @Transactional(readOnly = true)
    public DashboardSummaryDto getDashboardSummary() {
        Long totalAnalyses = analysisRunRepository.count();
        Long totalEvents = analysisRunRepository.countTotalEventsProcessed();
        Long totalAlerts = analysisRunRepository.countTotalAlertsGenerated();

        Map<String, Long> severityCounts = new HashMap<>();
        severityCounts.put("CRITICAL", securityAlertRepository.countBySeverityIgnoreCase("CRITICAL"));
        severityCounts.put("HIGH", securityAlertRepository.countBySeverityIgnoreCase("HIGH"));
        severityCounts.put("MEDIUM", securityAlertRepository.countBySeverityIgnoreCase("MEDIUM"));
        severityCounts.put("LOW", securityAlertRepository.countBySeverityIgnoreCase("LOW"));

        List<AnalysisRunDto> recentAnalyses = analysisRunRepository.findAllByOrderByStartedAtDesc().stream()
                .limit(5)
                .map(this::mapToDto)
                .collect(Collectors.toList());

        List<SecurityAlertDto> recentAlerts = securityAlertRepository.findTop10ByOrderByDetectedAtDesc().stream()
                .map(this::mapAlertToDto)
                .collect(Collectors.toList());

        return new DashboardSummaryDto(totalAnalyses, totalEvents, totalAlerts, severityCounts, recentAnalyses, recentAlerts);
    }

    public AnalysisRunDto mapToDto(AnalysisRun run) {
        return new AnalysisRunDto(
                run.getId(),
                run.getFileName(),
                run.getStartedAt(),
                run.getCompletedAt(),
                run.getTotalEvents(),
                run.getRejectedEvents(),
                run.getAlertCount(),
                run.getStatus()
        );
    }

    public AnalysisResponseDto mapToResponseDto(AnalysisRun run) {
        AnalysisResponseDto dto = new AnalysisResponseDto();
        dto.setAnalysisId(run.getId());
        dto.setFileName(run.getFileName());
        dto.setStartedAt(run.getStartedAt());
        dto.setCompletedAt(run.getCompletedAt());
        dto.setTotalEvents(run.getTotalEvents());
        dto.setRejectedEvents(run.getRejectedEvents());
        dto.setAlertCount(run.getAlertCount());
        dto.setStatus(run.getStatus());
        dto.setAlerts(run.getAlerts().stream().map(this::mapAlertToDto).collect(Collectors.toList()));
        return dto;
    }

    public SecurityAlertDto mapAlertToDto(SecurityAlert alert) {
        SecurityAlertDto dto = new SecurityAlertDto();
        dto.setId(alert.getId());
        if (alert.getAnalysisRun() != null) {
            dto.setAnalysisId(alert.getAnalysisRun().getId());
        }
        dto.setEventId(alert.getEventId());
        dto.setRuleId(alert.getRuleId());
        dto.setRuleName(alert.getRuleName());
        dto.setSeverity(alert.getSeverity());
        dto.setDescription(alert.getDescription());
        dto.setMitreTechniqueId(alert.getMitreTechniqueId());
        dto.setMitreTechniqueName(alert.getMitreTechniqueName());
        dto.setDetectedAt(alert.getDetectedAt());
        dto.setRawCommandLine(alert.getRawCommandLine());
        dto.setProcessName(alert.getProcessName());
        dto.setParentProcessName(alert.getParentProcessName());
        dto.setUserAccount(alert.getUserAccount());
        dto.setInvestigationStatus(alert.getInvestigationStatus());
        return dto;
    }
}
