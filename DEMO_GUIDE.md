# Faculty Demonstration Guide & Presentation Script
## Behavioral Detection Engine for Living-off-the-Land (LOLBin) Attacks

---

### Demonstration Overview
Use this step-by-step guide during your B.Tech project presentation and faculty demonstration. Follow the exact script below to explain the system cleanly.

---

### Step 1: Launch the Application (30 Seconds)
Open PowerShell in project root and execute:
```powershell
.\run_app.ps1
```
Explain to faculty:
> *"Respected faculty, I am starting both the Java Spring Boot REST backend on port 8085 and the React Vite frontend on port 5173 using our automated launch script."*

---

### Step 2: Explain the Problem Statement (1 Minute)
Open `http://localhost:5173` in browser.
> *"Traditional antivirus looks for malware file hashes on disk. But modern attackers abuse built-in Windows tools like PowerShell, WMI, and Certutil. Because these tools are signed by Microsoft, signature-based antivirus cannot block them. Our system solves this by analyzing process creation logs and command-line behavior heuristics."*

---

### Step 3: Demonstrate Security Log Upload (2 Minutes)
1. Click **"Upload Logs"** tab.
2. Select `backend/src/main/resources/sample-logs/sample_attack_logs.json`.
3. Click **"Analyze Security Logs"**.
> *"I am uploading a synthetic JSON process log. Our Spring Boot backend parses the events, validates the schema, passes them through our Java Behavioral Detection Engine, and persists the results in MySQL."*

---

### Step 4: Demonstrate the 3 Attack Scenarios (3 Minutes)
Click **"View Detailed Alerts →"**.

#### Scenario 1: PowerShell Defense Evasion (`RULE-PS-001`)
Click **Inspect** on PowerShell alert.
> *"Here we see powershell.exe executed with -ExecutionPolicy Bypass, -WindowStyle Hidden, and -EncodedCommand. Our engine flagged this as HIGH severity and mapped it to MITRE ATT&CK T1059.001 (PowerShell) and T1027 (Obfuscation)."*

#### Scenario 2: WMI Recovery Inhibition (`RULE-WMI-001`)
Click **Inspect** on WMI alert.
> *"Next is wmic.exe executing 'shadowcopy delete'. This is a CRITICAL severity ransomware technique used to destroy Volume Shadow Copies before encryption. Our engine mapped this to MITRE ATT&CK T1047 (WMI) and T1490 (Inhibit System Recovery)."*

#### Scenario 3: Certutil Ingress File Transfer (`RULE-CERT-001`)
Click **Inspect** on Certutil alert.
> *"Finally, certutil.exe was executed with -urlcache -split -f to download an external file. Attackers abuse certutil to bypass perimeter firewalls. Our engine mapped this to MITRE ATT&CK T1105 (Ingress Tool Transfer)."*

---

### Step 5: Demonstrate Alert Filtering & MySQL Persistence (2 Minutes)
1. Filter alerts by Severity: Select `CRITICAL`. Show only WMI alert remains.
2. Search query: Type `certutil`. Show live search table update.
3. Open MySQL Workbench and execute `SELECT * FROM lol_detector_db.security_alerts;`.
> *"All analysis sessions and alerts are stored in MySQL with foreign key constraints, enabling session auditing and status tracking."*
