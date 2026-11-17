# MITRE ATT&CK Technique Mapping Document
## Behavioral Detection Engine for Living-off-the-Land (LOLBin) Attacks

---

### Executive Overview
This document provides the formal mapping between the behavioral detection rules implemented in the **Behavioral Detection Engine** and the standardized **MITRE ATT&CK Enterprise Framework** (v14).

Living-off-the-Land Binaries (LOLBins) are legitimate, trusted system tools pre-installed on Microsoft Windows operating systems. Attackers abuse these binaries to perform fileless execution, defense evasion, ingress tool transfer, and recovery inhibition.

---

### MITRE ATT&CK Summary Table

| Rule ID | Detection Rule Name | Target Binary | Severity | MITRE Technique ID | MITRE Technique Name | Tactical Goal (Tactic) |
| :---: | :--- | :--- | :---: | :---: | :--- | :--- |
| `RULE-PS-001` | PowerShell Encoded Stealth Execution | `powershell.exe` / `pwsh.exe` | **HIGH** | **T1059.001** | Command and Scripting Interpreter: PowerShell | Execution (TA0002) |
| | | | | **T1027** | Obfuscated Files or Information | Defense Evasion (TA0005) |
| `RULE-WMI-001` | WMI Remote Execution & Shadow Copy Deletion | `wmic.exe` | **CRITICAL** | **T1047** | Windows Management Instrumentation | Execution (TA0002) |
| | | | | **T1490** | Inhibit System Recovery | Impact (TA0040) |
| `RULE-CERT-001` | Certutil Payload Ingress & Base64 Decoding Abuse | `certutil.exe` | **HIGH** | **T1105** | Ingress Tool Transfer | Command & Control (TA0011) |
| | | | | **T1140** | Deobfuscate/Decode Files or Information | Defense Evasion (TA0005) |

---

### Detailed Technique Analysis & Heuristics Breakdown

---

#### 1. Scenario A: PowerShell Abuse
- **Rule Identifier:** `RULE-PS-001`
- **Target Binary:** `powershell.exe` / `pwsh.exe`
- **Assigned Severity:** `HIGH`

##### MITRE ATT&CK Mappings:
1. **T1059.001 — Command and Scripting Interpreter: PowerShell**
   - **Tactic:** Execution (`TA0002`)
   - **Description:** Adversaries abuse PowerShell to execute commands, scripts, and fileless payloads directly in memory.
2. **T1027 — Obfuscated Files or Information**
   - **Tactic:** Defense Evasion (`TA0005`)
   - **Description:** Adversaries use Base64 encoding (`-EncodedCommand`) to conceal command text from string inspection.

##### Detection Heuristics & Matching Pattern:
The engine flags `powershell.exe` process creation events if the command line contains:
- Base64 encoding parameters (`-EncodedCommand`, `-enc`, `-e`)
- Stealth execution flags (`-WindowStyle Hidden`, `-w hidden`, `-NoProfile`, `-nop`, `-ExecutionPolicy Bypass`, `-ep bypass`)
- Memory download expressions (`Invoke-Expression`, `IEX`, `DownloadString`, `Invoke-WebRequest`)

##### False Positive Mitigation:
Standard administrative PowerShell execution (e.g., `powershell.exe Get-Process` or `powershell.exe Get-Service`) contains no stealth or encoding flags and is ignored by the engine (Zero False Positives).

---

#### 2. Scenario B: WMI Abuse
- **Rule Identifier:** `RULE-WMI-001`
- **Target Binary:** `wmic.exe`
- **Assigned Severity:** `CRITICAL`

##### MITRE ATT&CK Mappings:
1. **T1047 — Windows Management Instrumentation**
   - **Tactic:** Execution (`TA0002`)
   - **Description:** Adversaries abuse WMI to execute secondary processes locally or remotely (`wmic process call create`).
2. **T1490 — Inhibit System Recovery**
   - **Tactic:** Impact (`TA0040`)
   - **Description:** Ransomware operators execute `wmic shadowcopy delete` to destroy Volume Shadow Copies and prevent system recovery prior to encrypting files.

##### Detection Heuristics & Matching Pattern:
The engine flags `wmic.exe` process creation events if the command line contains:
- `shadowcopy delete` (Volume Shadow Copy destruction)
- `process call create` (Secondary process spawning)
- `/node:` (Remote system target execution)

---

#### 3. Scenario C: Certutil Abuse
- **Rule Identifier:** `RULE-CERT-001`
- **Target Binary:** `certutil.exe`
- **Assigned Severity:** `HIGH`

##### MITRE ATT&CK Mappings:
1. **T1105 — Ingress Tool Transfer**
   - **Tactic:** Command and Control (`TA0011`)
   - **Description:** Adversaries abuse `certutil.exe` `-urlcache` parameters to download external binary payloads past perimeter filters.
2. **T1140 — Deobfuscate/Decode Files or Information**
   - **Tactic:** Defense Evasion (`TA0005`)
   - **Description:** Adversaries use `certutil.exe -decode` to decode Base64-encoded binary payloads disguised as text files.

##### Detection Heuristics & Matching Pattern:
The engine flags `certutil.exe` process creation events if the command line contains:
- `-urlcache` or `/urlcache` combined with `-split`, `-f`, or HTTP/HTTPS URLs.
- `-decode` or `/decode` parameters.

---

### Authoritative References
- MITRE ATT&CK Framework: [https://attack.mitre.org/](https://attack.mitre.org/)
- LOLBAS Project (Living Off The Land Binaries and Scripts): [https://lolbas-project.github.io/](https://lolbas-project.github.io/)
- Microsoft Security Technical Documentation: Process Creation Audit Event 4688.
