package com.example.loldetector.service;

import com.example.loldetector.dto.SecurityAlertDto;
import com.example.loldetector.entity.SecurityAlert;
import com.example.loldetector.exception.ResourceNotFoundException;
import com.example.loldetector.repository.SecurityAlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlertService {

    private final SecurityAlertRepository securityAlertRepository;
    private final AnalysisService analysisService;

    @Autowired
    public AlertService(SecurityAlertRepository securityAlertRepository, AnalysisService analysisService) {
        this.securityAlertRepository = securityAlertRepository;
        this.analysisService = analysisService;
    }

    @Transactional(readOnly = true)
    public List<SecurityAlertDto> getAlerts(String severity, String search) {
        List<SecurityAlert> alerts;

        boolean hasSeverity = severity != null && !severity.isBlank() && !severity.equalsIgnoreCase("ALL");
        boolean hasSearch = search != null && !search.isBlank();

        if (hasSeverity && hasSearch) {
            alerts = securityAlertRepository.filterAndSearchAlerts(severity.trim(), search.trim());
        } else if (hasSeverity) {
            alerts = securityAlertRepository.findBySeverityIgnoreCase(severity.trim());
        } else if (hasSearch) {
            alerts = securityAlertRepository.searchAlerts(search.trim());
        } else {
            alerts = securityAlertRepository.findAll();
        }

        return alerts.stream()
                .map(analysisService::mapAlertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SecurityAlertDto getAlertById(Long id) {
        SecurityAlert alert = securityAlertRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Security Alert not found with ID: " + id));
        return analysisService.mapAlertToDto(alert);
    }

    @Transactional(readOnly = true)
    public List<SecurityAlertDto> getAlertsByAnalysisId(Long analysisId) {
        return securityAlertRepository.findByAnalysisRunId(analysisId).stream()
                .map(analysisService::mapAlertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public SecurityAlertDto updateInvestigationStatus(Long id, String status) {
        SecurityAlert alert = securityAlertRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Security Alert not found with ID: " + id));
        alert.setInvestigationStatus(status.toUpperCase());
        SecurityAlert updatedAlert = securityAlertRepository.save(alert);
        return analysisService.mapAlertToDto(updatedAlert);
    }
}
