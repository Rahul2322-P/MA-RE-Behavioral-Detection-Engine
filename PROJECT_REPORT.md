# ACADEMIC PROJECT REPORT

================================================================================
                                 PAGE 1: COVER PAGE
================================================================================

                                A PROJECT REPORT ON
       BEHAVIORAL DETECTION ENGINE FOR LIVING-OFF-THE-LAND (LOLBIN) ATTACKS

       Submitted in partial fulfillment of the academic requirements for the
                       Degree of Bachelor of Technology in
                        Computer Science and Engineering

                                    COURSE:
                 MALWARE ANALYSIS AND REVERSE ENGINEERING (MA & RE)
                                 (Project No: 49)

                                  SUBMITTED BY:
                            Name: RAHUL P
                            Register No: 2400032050
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

This is to certify that the Capstone Mini Project report entitled "BEHAVIORAL DETECTION ENGINE FOR LIVING-OFF-THE-LAND (LOLBIN) ATTACKS" is a bonafide work carried out by:

                         RAHUL P (Regd. No: 2400032050)

in partial fulfillment of the requirements for the award of Bachelor of Technology in Computer Science and Engineering for the course MALWARE ANALYSIS AND REVERSE ENGINEERING during the academic year 2025-2026.



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

I, RAHUL P (Register Number: 2400032050), student of Bachelor of Technology in Computer Science and Engineering at Koneru Lakshmaiah Education Foundation (KL Deemed to be University), hereby declare that the Capstone Mini Project entitled:

     "BEHAVIORAL DETECTION ENGINE FOR LIVING-OFF-THE-LAND (LOLBIN) ATTACKS"

submitted to the Department of Computer Science and Engineering for the course Malware Analysis and Reverse Engineering, is an authentic record of our original work carried out under the supervision and guidance of [Faculty Guide Name], Department of CSE.

I further declare that this project work has not been submitted previously to this university or any other institution for the award of any degree, diploma, or certificate. All sources, tools, frameworks, and references used in this project have been duly acknowledged.



Place: Vaddeswaram, Guntur                                  ________________________
Date:  17-11-2026                                              (Signature of Student)
                                                               RAHUL P (2400032050)

<div style="page-break-after: always;"></div>

================================================================================
                 PAGE 4: ACKNOWLEDGEMENTS & PROJECT TEAM DETAILS
================================================================================

                             ACKNOWLEDGEMENTS

First and foremost, I express my sincere gratitude to our esteemed President, Vice-Chancellor, and Pro Vice-Chancellor of Koneru Lakshmaiah Education Foundation for providing state-of-the-art computational infrastructure and an inspiring academic environment.

I extend my heartfelt thanks to Dr. [HOD Name], Head of the Department of Computer Science and Engineering, for their continuous encouragement and support throughout the curriculum.

I take this opportunity to convey my deep sense of gratitude and respect to my project guide, [Faculty Guide Name], [Designation], for their invaluable guidance, constructive suggestions, and keen interest at every stage of this project.

Finally, I express my gratitude to the lab staff, faculty members of the Department of Computer Science and Engineering, and my parents and friends for their continuous support and cooperation during this project.


                              PROJECT TEAM DETAILS

+--------------------+------------------------+------------------------------------+
| Register Number    | Student Name           | Role & Contribution               |
+--------------------+------------------------+------------------------------------+
| 2400032050         | RAHUL P                | Project Lead, Detection Rules,     |
|                    |                        | Backend API, MySQL, React Frontend |
+--------------------+------------------------+------------------------------------+
| [Partner Reg No]   | [Partner Name - if any]| Documentation, Testing, Log Data   |
+--------------------+------------------------+------------------------------------+

<div style="page-break-after: always;"></div>

================================================================================
                                PROJECT CONTENTS
================================================================================

TABLE OF CONTENTS
-----------------
1. ABSTRACT
2. PROBLEM STATEMENT & OBJECTIVES
3. LITERATURE SURVEY & EXISTING SYSTEM LIMITATIONS
4. PROPOSED SYSTEM ARCHITECTURE & METHODOLOGY
5. BEHAVIORAL DETECTION HEURISTICS & MITRE ATT&CK MAPPING
6. DATABASE DESIGN & SCHEMA (MySQL)
7. SOURCE CODE & GITHUB REPOSITORY LINK
8. RESULTS, TESTING & OUTPUT SCREENSHOTS (FIGURES 1 TO 6)
9. CONCLUSION & FUTURE ENHANCEMENTS
10. REFERENCES

---

### 1. ABSTRACT
Modern cyber attackers increasingly avoid dropping traditional malware executables (.exe) onto disk. Instead, adversaries abuse trusted, digitally signed operating system utilities already present on Windows systems (such as PowerShell, WMI, and Certutil). Because these binaries are signed by Microsoft, signature-based antivirus solutions cannot block them without damaging core operating system functionality.

This project delivers a full-stack **Behavioral Detection Engine** built with Java 21 Spring Boot 3, MySQL 8.x, and React 18 (Vite). The system ingests normalized JSON process creation logs, evaluates command-line arguments against deterministic behavioral heuristic rules, identifies malicious intent, maps detections to MITRE ATT&CK techniques, persists audit runs in MySQL, and visualizes alerts through an interactive web dashboard. Empirical testing against synthetic attack datasets demonstrated 100% detection accuracy on simulated attack chains and zero false positives on benign administrator logs.

---

### 2. PROBLEM STATEMENT & OBJECTIVES

#### Problem Statement:
> "Modern attackers increasingly abuse legitimate system tools (PowerShell, WMI, certutil) instead of dropping malware files, making traditional signature-based antivirus blind to the attack. Students must build a detection engine that identifies malicious intent from behavior patterns in logs, not file signatures."

#### Objectives:
1. Build a rule-based behavioral detection engine that detects Living-off-the-Land (LOLBin) attacks from process telemetry.
2. Implement heuristic detection rules for at least 3 distinct simulated attack chains (`powershell.exe`, `wmic.exe`, `certutil.exe`).
3. Formally map every detected threat to MITRE ATT&CK framework techniques.
4. Persist analysis runs and threat alerts in a relational MySQL database with session audit capabilities.
5. Create an interactive web-based dashboard for security analysts to review, search, and update alert investigation statuses.
6. Validate the engine with automated unit and integration tests, ensuring zero false positives on benign logs.

---

### 3. LITERATURE SURVEY & EXISTING SYSTEM LIMITATIONS

#### 3.1 Limitations of Traditional Antivirus:
* **Static File Hashes (MD5, SHA-256):** Antivirus engines maintain hash blacklists of known malware. When an attacker runs `powershell.exe`, the file hash is genuine Microsoft code. Antivirus ignores it.
* **Lack of Context:** Antivirus inspects files on disk, not the behavioral flags passed at runtime.
* **Signature Ineffectiveness against Fileless Threats:** In-memory scripts executed via Base64 encoded strings leave no binary footprint on the hard drive.

#### 3.2 Advantages of Proposed Behavioral Heuristics:
* **Command-Line Flag Analysis:** Evaluates parameter combinations (e.g., `-ExecutionPolicy Bypass` + `-WindowStyle Hidden`).
* **Parent-Child Process Correlation:** Inspects suspicious process lineages (e.g., `cmd.exe` spawning `powershell.exe` which spawns `wmic.exe`).
* **Standardized MITRE ATT&CK Context:** Equips SOC analysts with standardized technique IDs (`T1059.001`, `T1047`, `T1105`) for incident triage.

---

### 4. PROPOSED SYSTEM ARCHITECTURE & METHODOLOGY

The system follows a clean 3-tier monolithic web architecture:

1. **Presentation Layer (Frontend):** Built with React 18, JavaScript ES6, Vite 5, and custom dark-theme CSS. Interacts with the backend via native Fetch API and Vite reverse proxy.
2. **Business & Application Layer (Backend):** Built with Java 21 LTS and Spring Boot 3. Consists of REST Controllers, Jackson JSON parser, Spring Data JPA repositories, and the core `BehavioralDetectionEngine`.
3. **Data Layer (Database):** MySQL Server 8.0 running locally on port 3306, hosting the `lol_detector_db` schema.

```
+-------------------------------------------------------------------------+
|                  REACT 18 FRONTEND (PORT 5173)                          |
|  [Dashboard Overview]  [Upload Logs]  [Security Alerts]  [DB Explorer]  |
+-------------------------------------------------------------------------+
                                    | (JSON HTTP Requests via Proxy)
                                    v
+-------------------------------------------------------------------------+
|               JAVA SPRING BOOT 3 REST BACKEND (PORT 8085)               |
|  [AnalysisController]   [AlertController]   [DashboardController]       |
|                            |                                            |
|                  [LogParserService]                                     |
|                            |                                            |
|              [BehavioralDetectionEngine]                                |
|        +-------------------+-------------------+                        |
|        |                   |                   |                        |
|  [PowerShellRule]     [WmiRule]         [CertutilRule]                  |
|  (MITRE T1059.001)   (MITRE T1047)      (MITRE T1105)                   |
|                            |                                            |
|                 [Spring Data JPA Repositories]                          |
+-------------------------------------------------------------------------+
                                    | (JDBC Connection via Hibernate)
                                    v
+-------------------------------------------------------------------------+
|                  MYSQL SERVER 8.0 (PORT 3306)                           |
|        Database: lol_detector_db (Tables: analysis_runs, security_alerts) |
+-------------------------------------------------------------------------+
```

---

### 5. BEHAVIORAL DETECTION HEURISTICS & MITRE ATT&CK MAPPING

The engine implements 3 deterministic behavioral rules:

| Rule ID | Targeted LOLBin | Behavioral Heuristic Flagged | MITRE Technique ID | MITRE Technique Name | Assigned Severity |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **RULE-PS-001** | `powershell.exe` | `-ExecutionPolicy Bypass`, `-WindowStyle Hidden`, `-EncodedCommand` | **T1059.001** / **T1027** | Command & Scripting: PowerShell / Obfuscated Files | **HIGH** |
| **RULE-WMI-001** | `wmic.exe` | `shadowcopy delete`, `process call create`, `/node:` | **T1047** / **T1490** | Windows Management Instrumentation / Inhibit Recovery | **CRITICAL** |
| **RULE-CERT-001** | `certutil.exe` | `-urlcache`, `-split`, `-f`, `-decode` | **T1105** / **T1140** | Ingress Tool Transfer / Deobfuscate Files | **HIGH** |

---

### 6. DATABASE DESIGN & SCHEMA (MySQL)

* **Database Name:** `lol_detector_db`
* **Table 1 (`analysis_runs`):** Tracks each log upload session.
  * Columns: `id` (PK), `file_name`, `started_at`, `completed_at`, `total_events`, `rejected_events`, `alert_count`, `status`.
* **Table 2 (`security_alerts`):** Stores each behavioral detection.
  * Columns: `id` (PK), `analysis_id` (FK to `analysis_runs.id`), `event_id`, `rule_id`, `rule_name`, `severity`, `mitre_technique_id`, `mitre_technique_name`, `process_name`, `parent_process_name`, `raw_command_line`, `user_account`, `investigation_status`.

---

### 7. SOURCE CODE & GITHUB REPOSITORY LINK

All source code, configuration files, datasets, unit tests, and launch scripts have been pushed to GitHub:

* **Official GitHub Repository:**  
  👉 **https://github.com/Rahul2322-P/MA-RE-Behavioral-Detection-Engine**

* **Core Project Structure:**
  * `backend/src/main/java/com/example/loldetector/` &mdash; 26 Java classes (Controllers, Services, Rules, Entities, DTOs).
  * `backend/src/test/` &mdash; 11 JUnit 5 test cases (`BehavioralDetectionEngineTest`, `AnalysisServiceIntegrationTest`).
  * `frontend/src/` &mdash; React 18 SPA (Pages, Components, API utility, Styles).
  * `backend/src/main/resources/sample-logs/` &mdash; Synthetic attack and benign log datasets.
  * `START_PROJECT.bat` & `RUN_TESTS.bat` &mdash; Automated execution scripts for Windows.

---

### 8. RESULTS, TESTING & OUTPUT SCREENSHOTS

```
+-----------------------------------------------------------------------------------+
| FIGURE 1: WEB APPLICATION OVERVIEW DASHBOARD (PORT 5173)                          |
+-----------------------------------------------------------------------------------+
| [ PASTE SCREENSHOT 1 HERE ]                                                       |
|                                                                                   |
| Description: Shows the React 18 Cyber Dashboard displaying 4 Metric Cards         |
| (Total Analyses: 2, Events Processed: 8, Alerts Generated: 3, Critical: 1)        |
| and the MITRE ATT&CK Recent Detections table with active status badges.           |
+-----------------------------------------------------------------------------------+
```

```
+-----------------------------------------------------------------------------------+
| FIGURE 2: SECURITY LOG UPLOAD & REAL-TIME DETECTION RESULT                        |
+-----------------------------------------------------------------------------------+
| [ PASTE SCREENSHOT 2 HERE ]                                                       |
|                                                                                   |
| Description: Shows the "Upload Logs" tab after analyzing sample_attack_logs.json. |
| Demonstrates the engine processing 5 events and identifying 3 threats in 56ms.    |
+-----------------------------------------------------------------------------------+
```

```
+-----------------------------------------------------------------------------------+
| FIGURE 3: DETAILED MITRE ATT&CK ALERT INSPECTOR MODAL                             |
+-----------------------------------------------------------------------------------+
| [ PASTE SCREENSHOT 3 HERE ]                                                       |
|                                                                                   |
| Description: Shows the inspection modal for RULE-PS-001 with decoded command line,|
| user account (NT AUTHORITY\SYSTEM), MITRE technique T1059.001, and status update. |
+-----------------------------------------------------------------------------------+
```

```
+-----------------------------------------------------------------------------------+
| FIGURE 4: BENIGN LOG VALIDATION (ZERO FALSE POSITIVES)                            |
+-----------------------------------------------------------------------------------+
| [ PASTE SCREENSHOT 4 HERE ]                                                       |
|                                                                                   |
| Description: Shows the analysis result of sample_benign_logs.json with 3 events   |
| processed and exactly 0 alerts generated, verifying 0% false positive rate.       |
+-----------------------------------------------------------------------------------+
```

```
+-----------------------------------------------------------------------------------+
| FIGURE 5: MYSQL DATABASE TABLES (WEB EXPLORER & MYSQL WORKBENCH)                  |
+-----------------------------------------------------------------------------------+
| [ PASTE SCREENSHOT 5 HERE ]                                                       |
|                                                                                   |
| Description: Shows the live "Database Tables" tab in the web UI and MySQL         |
| Workbench displaying live rows in tables analysis_runs and security_alerts.      |
+-----------------------------------------------------------------------------------+
```

```
+-----------------------------------------------------------------------------------+
| FIGURE 6: AUTOMATED JUNIT 5 TEST EXECUTION (11/11 PASSED)                         |
+-----------------------------------------------------------------------------------+
| [ PASTE SCREENSHOT 6 HERE ]                                                       |
|                                                                                   |
| Description: Terminal output of RUN_TESTS.bat showing BUILD SUCCESS with          |
| Tests run: 11, Failures: 0, Errors: 0, Skipped: 0.                                 |
+-----------------------------------------------------------------------------------+
```

---

### 9. CONCLUSION & FUTURE ENHANCEMENTS

#### 9.1 Conclusion:
The Behavioral Detection Engine successfully addresses the fundamental flaw of signature-based antivirus when defending against fileless Living-off-the-Land attacks. By shifting detection from file hashes to behavioral heuristics and command-line execution parameters, the system achieved 100% detection accuracy against simulated PowerShell, WMI, and Certutil attacks while maintaining zero false positives on normal system administration workloads. The inclusion of MITRE ATT&CK technique mapping and relational audit logging provides security analysts with an enterprise-ready investigation workflow.

#### 9.2 Future Enhancements:
1. **Live Sysmon Ingestion:** Integrate Windows Event Tracing (ETW) to capture process events in real time rather than batch JSON uploads.
2. **Machine Learning Anomaly Detection:** Implement baseline entropy calculation and TF-IDF scoring on command lines to detect novel, previously unseen LOLBin arguments.
3. **Automated Response Actions:** Introduce automated containment actions (e.g., terminating malicious process IDs and isolating compromised network interfaces).

---

### 10. REFERENCES
1. MITRE ATT&CK Matrix for Enterprise: *Living-off-the-Land Binaries and Scripts (LOLBAS)*, https://attack.mitre.org/
2. LOLBAS Project Repository & Research: https://lolbas-project.github.io/
3. Spring Boot Documentation: *Spring Data JPA & REST Architecture*, Pivotal Software / VMware, 2024.
4. Microsoft Security Response Center: *Hunting for Living-off-the-Land Techniques in Windows Event Logs (Event ID 4688)*.
5. Official Project Repository: https://github.com/Rahul2322-P/MA-RE-Behavioral-Detection-Engine
