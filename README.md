# Behavioral Detection Engine for Living-off-the-Land (LOLBin) Attacks
> **Course:** Malware Analysis & Reverse Engineering (MA & RE)  
> **Repository:** `https://github.com/Rahul2322-P/MA-RE-Behavioral-Detection-Engine`  
> *A Rule-Based Security Log Analysis & MITRE ATT&CK Mapping System*

---

## 📌 Project Overview
Modern cyber attackers increasingly abuse legitimate Windows system utilities (`powershell.exe`, `wmic.exe`, `certutil.exe`) instead of dropping untrusted malware executables. Because these binaries are digitally signed by Microsoft, traditional signature-based antivirus scanners cannot block them without damaging operating system functionality.

This capstone project implements a full-stack **Behavioral Detection Engine** that ingests process creation event logs, evaluates command-line arguments against behavioral heuristic rules, identifies malicious intent, maps detections to MITRE ATT&CK techniques, stores audit runs in MySQL, and displays analytics via a React dashboard.

---

## 🛠️ Technology Stack
- **Backend:** Java 17 / 21, Spring Boot 3.2.3, Spring Data JPA, Maven.
- **Frontend:** React 18, JavaScript (ES6+), Vite 5, HTML5, CSS3, Fetch API.
- **Database:** MySQL 8.x relational database.
- **Testing:** JUnit 5, Mockito, Spring Boot Test.
- **Input Format:** JSON process creation logs.

---

## ⚙️ Prerequisites & Environment Setup
Before running the application, ensure the following software is installed on your Windows machine:
1. **Java Development Kit (JDK 17 or 21)**
2. **Node.js (v18+ or v20+) & npm**
3. **MySQL Server 8.x** running on `localhost:3306`

---

## 🚀 Quick Start Guide (Windows)

### 1. Database Configuration
Open MySQL Workbench or MySQL Command Line and ensure MySQL service is running. Database schema creation is handled automatically on startup, or you can run `backend/src/main/resources/schema.sql`.

Update MySQL credentials in `backend/src/main/resources/application.properties` if your local password is not `root`:
```properties
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

### 2. Run Applications Simultaneously (Recommended)
Open Windows Terminal / PowerShell in the project root directory and execute:

```powershell
.\run_app.ps1
```

This launch script automatically starts:
- **Backend Server:** `http://localhost:8085/api`
- **Frontend Dashboard:** `http://localhost:5173`

### 3. Manual Execution (Alternative)

#### Terminal A: Start Backend
```powershell
cd backend
.\run_backend.ps1
```

#### Terminal B: Start Frontend
```powershell
cd frontend
npm install
npm run dev
```

---

## 🧪 Demonstration Steps

1. Open browser at `http://localhost:5173`.
2. Click **"Upload Logs"** tab.
3. Click **"Browse Log File..."** and select `backend/src/main/resources/sample-logs/sample_attack_logs.json`.
4. Click **"Analyze Security Logs"**.
5. Observe 3 detected alerts (`RULE-PS-001`, `RULE-WMI-001`, `RULE-CERT-001`).
6. Click **"Security Alerts"** tab to search by keyword or filter by severity (`CRITICAL`, `HIGH`).
7. Click **"Inspect"** to review raw command lines and MITRE ATT&CK mappings.

---

## 🔬 Running Unit & Integration Tests

```powershell
cd backend
./mvnw test
```

All 11 unit and integration test cases will execute and verify 100% test pass rate.
