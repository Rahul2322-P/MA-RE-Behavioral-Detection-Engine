package com.example.loldetector.controller;

import com.example.loldetector.dto.DashboardSummaryDto;
import com.example.loldetector.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final AnalysisService analysisService;

    @Autowired
    public DashboardController(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @GetMapping("/summary")
    public ResponseEntity<DashboardSummaryDto> getDashboardSummary() {
        DashboardSummaryDto summary = analysisService.getDashboardSummary();
        return ResponseEntity.ok(summary);
    }
}
