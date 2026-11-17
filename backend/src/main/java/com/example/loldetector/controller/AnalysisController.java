package com.example.loldetector.controller;

import com.example.loldetector.detection.BehavioralDetectionEngine;
import com.example.loldetector.dto.AnalysisResponseDto;
import com.example.loldetector.dto.AnalysisRunDto;
import com.example.loldetector.dto.LogEventDto;
import com.example.loldetector.dto.SecurityAlertDto;
import com.example.loldetector.entity.AnalysisRun;
import com.example.loldetector.entity.SecurityAlert;
import com.example.loldetector.service.AlertService;
import com.example.loldetector.service.AnalysisService;
import com.example.loldetector.service.LogParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/analyses")
public class AnalysisController {

    private final LogParserService logParserService;
    private final BehavioralDetectionEngine detectionEngine;
    private final AnalysisService analysisService;
    private final AlertService alertService;

    @Autowired
    public AnalysisController(LogParserService logParserService, 
                              BehavioralDetectionEngine detectionEngine, 
                              AnalysisService analysisService, 
                              AlertService alertService) {
        this.logParserService = logParserService;
        this.detectionEngine = detectionEngine;
        this.analysisService = analysisService;
        this.alertService = alertService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AnalysisResponseDto> uploadAndAnalyzeLogFile(@RequestParam("file") MultipartFile file) {
        List<LogEventDto> events = logParserService.parseLogFile(file);

        String originalFileName = file.getOriginalFilename() != null ? file.getOriginalFilename() : "uploaded_log.json";
        AnalysisRun initialRun = analysisService.createInitialRun(originalFileName, events.size(), 0);

        List<SecurityAlert> detectedAlerts = detectionEngine.analyzeEvents(events);
        AnalysisResponseDto response = analysisService.completeAnalysisRun(initialRun, detectedAlerts);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<AnalysisRunDto>> getAllAnalyses() {
        List<AnalysisRunDto> history = analysisService.getAllAnalysisRuns();
        return ResponseEntity.ok(history);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnalysisResponseDto> getAnalysisById(@PathVariable Long id) {
        AnalysisResponseDto analysis = analysisService.getAnalysisById(id);
        return ResponseEntity.ok(analysis);
    }

    @GetMapping("/{id}/alerts")
    public ResponseEntity<List<SecurityAlertDto>> getAlertsByAnalysisId(@PathVariable Long id) {
        List<SecurityAlertDto> alerts = alertService.getAlertsByAnalysisId(id);
        return ResponseEntity.ok(alerts);
    }
}
