# Behavioral Detection Engine for Living-off-the-Land (LOLBin) Attacks

> **Course:** Malware Analysis & Reverse Engineering (MA & RE)  
> **Topic:** Option 2: Behavioral Detection Engine for Living-off-the-Land Attacks (Project No. 49 / PS-2)  
> **Repository:** https://github.com/Rahul2322-P/MA-RE-Behavioral-Detection-Engine  

---

## 📌 Problem Statement (PS-2)
> *"Modern attackers increasingly abuse legitimate system tools (PowerShell, WMI, certutil) instead of dropping malware files, making traditional signature-based antivirus blind to the attack. Students must build a detection engine that identifies malicious intent from behavior patterns in logs, not file signatures."*

### Objective:
Detect fileless/LOLBin-based attacks using log analysis and behavioral heuristics mapped to MITRE ATT&CK techniques.

---

## 🛠️ Technology Stack
- **Backend:** Java 21, Spring Boot 3.2.3, Spring Data JPA, Hibernate, Maven.
- **Frontend:** React 18, Vite 5, JavaScript (ES6+), Custom Cyber Theme CSS.
- **Database:** MySQL Server 8.0 (`lol_detector_db`, port 3306).
- **Testing:** JUnit 5, Mockito, Spring Boot Test (11 passing tests).

---

## ⚙️ Prerequisites
Ensure the following tools are installed on your machine:
1. **Java JDK 17 or 21** (`java -version`)
2. **Node.js v18+ or v20+** (`node -v`)
3. **MySQL Server 8.0** running on `localhost:3306`

---

## 🚀 Step-by-Step Execution Guide

### Step 1: Database Setup
1. Start your local MySQL Server on port `3306`.
2. Configure credentials in `backend/src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/lol_detector_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
   spring.datasource.username=root
   spring.datasource.password=YOUR_MYSQL_PASSWORD
   ```
   *(Database schema and tables are auto-created on first run via Hibernate ORM).*

### Step 2: Start the Project (1-Click Method)
Double-click **`START_PROJECT.bat`** in the root directory.

This automatically launches both services in separate terminal windows:
- **Backend Server:** `http://localhost:8085/api`
- **Frontend Dashboard:** `http://localhost:5173`

*(Alternatively, for manual start):*
- **Backend:** `cd backend && .\mvnw spring-boot:run`
- **Frontend:** `cd frontend && npm install && npm run dev`

### Step 3: Run the Live Demonstration
1. Open your web browser and navigate to: `http://localhost:5173`
2. Go to the **"Upload Logs"** tab.
3. Select the sample attack log file:
   `backend/src/main/resources/sample-logs/sample_attack_logs.json`
4. Click **"Analyze Security Logs"**.
5. Observe the **3 LOLBin threats detected in real time**:
   - `powershell.exe` &rarr; `RULE-PS-001` (MITRE `T1059.001` / `T1027`) - HIGH
   - `wmic.exe` &rarr; `RULE-WMI-001` (MITRE `T1047` / `T1490`) - CRITICAL
   - `certutil.exe` &rarr; `RULE-CERT-001` (MITRE `T1105` / `T1140`) - HIGH
6. Click **"View"** on any alert to inspect the raw obfuscated command line, user account, and MITRE technique details.
7. Go to **"Database Tables"** tab to verify live persistence in MySQL tables `analysis_runs` and `security_alerts`.

### Step 4: Validate Benign Baseline (Zero False Positives)
1. On the **"Upload Logs"** tab, select:
   `backend/src/main/resources/sample-logs/sample_benign_logs.json`
2. Click **"Analyze Security Logs"**.
3. Observe **0 threats flagged across 3 administrative events** (0% False Alarm Rate).

### Step 5: Run Automated Tests
Double-click **`RUN_TESTS.bat`** (or execute `cd backend && mvn test`).
- All **11 JUnit 5 test cases** will run and pass with **BUILD SUCCESS**.

---

## 🛡️ Heuristic Rules & MITRE ATT&CK Mapping Matrix

| Rule ID | Process | MITRE Technique ID | MITRE Technique Name | Severity | Detection Logic |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `RULE-PS-001` | `powershell.exe` | **T1059.001** / **T1027** | Command and Scripting: PowerShell | **HIGH** | `-ExecutionPolicy Bypass`, `-WindowStyle Hidden`, `-EncodedCommand` |
| `RULE-WMI-001` | `wmic.exe` | **T1047** / **T1490** | WMI / Inhibit System Recovery | **CRITICAL** | `shadowcopy delete`, `process call create`, `/node:` |
| `RULE-CERT-001` | `certutil.exe` | **T1105** / **T1140** | Ingress Tool Transfer | **HIGH** | `-urlcache`, `-split`, `-f` with remote HTTP/HTTPS URLs |

---

## 📂 Project Repository Structure
```
MA-RE-Behavioral-Detection-Engine/
├── backend/                             # Java 21 Spring Boot 3 REST Backend
│   ├── src/main/java/com/example/       # Controllers, Services, Detection Engine, Heuristic Rules, Entities
│   ├── src/main/resources/              # application.properties, sample-logs, schema.sql
│   └── src/test/java/                   # JUnit 5 Unit & Integration Test Suite (11 Tests)
├── frontend/                            # React 18 Single-Page Application (Vite 5)
│   ├── src/                             # Dashboard, UploadLogs, Alerts, DatabaseExplorer, Components
│   └── package.json                     # Frontend dependencies
├── START_PROJECT.bat                    # 1-Click Launch Script for Windows
├── RUN_TESTS.bat                        # 1-Click Automated Test Runner
├── .gitignore                           # Git ignore rules
└── README.md                            # Complete Project Documentation & Guide
```
