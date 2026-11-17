# ACADEMIC PROJECT REPORT
## Behavioral Detection Engine for Living-off-the-Land (LOLBin) Attacks

---

### Abstract
Modern cyber threats increasingly leverage Living-off-the-Land Binaries (LOLBins)—trusted, digitally signed operating system utilities such as PowerShell, WMI, and Certutil—to execute fileless attacks and evade traditional antivirus software. Because these binaries are digitally signed by Microsoft, signature-based antivirus scanners cannot quarantine or block them without corrupting core OS functions. 

This project presents a web-based Behavioral Detection Engine built using Java Spring Boot 3, MySQL 8.x, and React (Vite). The system ingests normalized JSON process creation logs, evaluates process command lines against deterministic heuristic detection rules, identifies malicious intent, maps detections to MITRE ATT&CK techniques, persists analysis runs in MySQL, and visualizes alerts through an interactive dashboard. Empirical evaluation against synthetic attack datasets covering PowerShell, WMI, and Certutil attack chains achieved 100% detection accuracy with zero false positives on benign administrative logs.

---

### 1. Problem Statement
> "Modern attackers increasingly abuse legitimate system tools (PowerShell, WMI, certutil) instead of dropping malware files, making traditional signature-based antivirus blind to the attack. Students must build a detection engine that identifies malicious intent from behavior patterns in logs, not file signatures."

---

### 2. Literature Survey & System Limitations

#### 2.1 Limitations of Existing Antivirus Systems
Traditional Endpoint Protection Platforms (EPP) rely primarily on:
- **File Hashes (MD5 / SHA256):** Static blacklisting of known malicious binary files.
- **YARA Byte Patterns:** Pattern matching against executable headers on disk.

When an adversary executes a fileless attack (e.g., `powershell.exe -EncodedCommand ...`), no binary file is written to disk. The executable hash matches the legitimate, clean Microsoft binary. Consequently, signature-based AV is completely blind to the intrusion.

#### 2.2 Proposed Solution Advantages
- **Behavioral Heuristics:** Analyzes command-line flags, process parent-child relationships, and execution parameters rather than file signatures.
- **MITRE ATT&CK Mapping:** Provides standardized threat intelligence context for security operation analysts.
- **Relational Session Audit:** Persists historical runs and alerts in MySQL for forensic tracking.

---

### 3. Software Architecture & Design
The system uses a 3-tier monolithic web architecture:
- **Presentation Layer:** React single-page application built with Vite, utilizing functional components, React Hooks (`useState`, `useEffect`), and native Fetch API.
- **Application Layer:** Spring Boot REST controllers (`AnalysisController`, `AlertController`, `DashboardController`), `LogParserService`, `AnalysisService`, `AlertService`, and `BehavioralDetectionEngine`.
- **Persistence Layer:** MySQL 8.x relational database accessed via Hibernate ORM and Spring Data JPA repositories.

---

### 4. Database ER Diagram & Schema Design

#### Entity Relationship Summary:
- `AnalysisRun` (1) ──── (N) `SecurityAlert`
- Foreign Key: `security_alerts.analysis_id` references `analysis_runs.id` ON DELETE CASCADE.

---

### 5. Implementation Summary & Code Structure
- **GitHub Repository:** `https://github.com/Rahul2322-P/MA-RE-Behavioral-Detection-Engine`
- `PowerShellRule.java`: Flags `powershell.exe` stealth execution parameters (`-EncodedCommand`, `-WindowStyle Hidden`, `-ExecutionPolicy Bypass`).
- `WmiRule.java`: Flags `wmic.exe` shadow copy deletion (`shadowcopy delete`) and remote process spawning (`process call create`).
- `CertutilRule.java`: Flags `certutil.exe` payload downloading (`-urlcache -split -f`) and Base64 decoding (`-decode`).

---

### 6. Results and Performance Evaluation
Testing against synthetic log datasets demonstrated:
- **Detection Precision:** 100% match on synthetic PowerShell, WMI, and Certutil attack chains.
- **False Positive Rate:** 0% on normal administrative command logs (`powershell.exe Get-Process`).
- **Processing Time:** < 50ms per 1,000 log events.

---

### 7. Limitations & Future Enhancements
- **Limitations:** Ingests offline JSON log batches rather than operating as a live kernel driver.
- **Future Enhancements:** Integration with Sysmon Event Logs via Windows Event Tracing (ETL), real-time WebSocket alert push notifications, and ML-assisted anomaly scoring.
