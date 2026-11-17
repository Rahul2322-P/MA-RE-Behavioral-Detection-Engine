package com.example.loldetector.controller;

import com.example.loldetector.dto.SecurityAlertDto;
import com.example.loldetector.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertService alertService;

    @Autowired
    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @GetMapping
    public ResponseEntity<List<SecurityAlertDto>> getAlerts(
            @RequestParam(required = false) String severity,
            @RequestParam(required = false) String search) {
        List<SecurityAlertDto> alerts = alertService.getAlerts(severity, search);
        return ResponseEntity.ok(alerts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SecurityAlertDto> getAlertById(@PathVariable Long id) {
        SecurityAlertDto alert = alertService.getAlertById(id);
        return ResponseEntity.ok(alert);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<SecurityAlertDto> updateAlertStatus(
            @PathVariable Long id, 
            @RequestBody Map<String, String> body) {
        String status = body.getOrDefault("status", "NEW");
        SecurityAlertDto updatedAlert = alertService.updateInvestigationStatus(id, status);
        return ResponseEntity.ok(updatedAlert);
    }
}
