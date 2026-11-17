# Viva Voce Question & Answer Guide
## Behavioral Detection Engine for Living-off-the-Land (LOLBin) Attacks

---

### Key Questions & Model Answers for Project Viva

#### Q1: What is a Living-off-the-Land Binary (LOLBin)?
**Answer:**  
LOLBins are legitimate, digitally signed operating system utilities (such as `powershell.exe`, `wmic.exe`, `certutil.exe`) pre-installed on Windows. Attackers abuse these trusted tools to execute malicious commands, download external payloads, or evade detection without dropping untrusted binary files on disk.

---

#### Q2: Why does traditional signature-based antivirus fail against fileless attacks?
**Answer:**  
Traditional antivirus relies on static file signatures (e.g., MD5/SHA256 hashes or byte patterns of known malware executables). In a fileless attack, the binary being executed is `powershell.exe`—a clean executable signed by Microsoft. Antivirus cannot delete or block `powershell.exe` without breaking Windows operating system functionality.

---

#### Q3: How does your Behavioral Detection Engine identify malicious intent?
**Answer:**  
Our engine inspects **process creation logs** (such as Event ID 4688). Rather than checking file hashes, it evaluates command-line arguments and flags against behavioral rules. For example, if `powershell.exe` is launched with `-ExecutionPolicy Bypass`, `-WindowStyle Hidden`, and `-EncodedCommand`, the engine identifies stealth evasion patterns and flags the event.

---

#### Q4: Explain the 3 attack scenarios implemented in your project.
**Answer:**  
1. **PowerShell (`RULE-PS-001`):** Flags execution policy bypass, hidden windows, and Base64 encoded payload execution. Mapped to MITRE `T1059.001` & `T1027`.
2. **WMI (`RULE-WMI-001`):** Flags `wmic.exe shadowcopy delete` (used by ransomware to destroy backups) and remote process creation (`wmic process call create`). Mapped to MITRE `T1047` & `T1490`.
3. **Certutil (`RULE-CERT-001`):** Flags `certutil.exe` abusing `-urlcache -split -f` to download external files or `-decode` to unpack Base64 payloads. Mapped to MITRE `T1105` & `T1140`.

---

#### Q5: How did you achieve zero false positives on benign administrative commands?
**Answer:**  
Our heuristic rules require specific combinations of evasion flags (e.g., requiring at least 2 stealth flags or Base64 encoding parameters). Standard administrative commands like `powershell.exe Get-Process` or `wmic.exe cpu get name` contain zero stealth parameters and are ignored by the engine.

---

#### Q6: Explain the architecture of your full-stack application.
**Answer:**  
The project uses a monolithic 3-tier web architecture:
- **Presentation Layer:** React single-page app built with Vite and standard CSS.
- **Application Layer:** Java 17 Spring Boot REST APIs (`AnalysisController`, `AlertController`, `DashboardController`), `LogParserService`, `AnalysisService`, and `BehavioralDetectionEngine`.
- **Database Layer:** MySQL 8.x database managed using Spring Data JPA and Hibernate ORM.

---

#### Q7: How do you handle database persistence and prevent duplicate records?
**Answer:**  
Every log upload creates a distinct `AnalysisRun` session entity in MySQL with a primary key ID. Generated alerts are linked to the session via a `@ManyToOne` foreign key (`security_alerts.analysis_id`). Retrying or re-analyzing logs creates a new isolated session run ID, preventing cross-session alert duplication.

---

#### Q8: What is the MITRE ATT&CK Framework and why did you use it?
**Answer:**  
MITRE ATT&CK is a globally accessible, standardized knowledge base of adversary tactics and techniques based on real-world observations. Mapping our detections to MITRE IDs (e.g., `T1059.001`, `T1490`, `T1105`) gives security analysts standardized context regarding the adversary's tactical goals.
