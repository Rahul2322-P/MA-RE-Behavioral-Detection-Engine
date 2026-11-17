package com.example.loldetector.detection.rules;

import com.example.loldetector.detection.DetectionRule;
import com.example.loldetector.dto.LogEventDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PowerShellRule extends DetectionRule {

    private static final List<String> STEALTH_FLAGS = List.of(
            "-encodedcommand", "-enc", "-e ",
            "-windowstyle hidden", "-w hidden",
            "-nop", "-noprofile",
            "-executionpolicy bypass", "-ep bypass",
            "invoke-expression", "iex",
            "invoke-webrequest", "downloadstring"
    );

    public PowerShellRule() {
        super(
                "RULE-PS-001",
                "PowerShell Encoded Stealth Execution",
                "Detects suspicious PowerShell execution utilizing encoded commands, execution policy bypass, hidden windows, or remote script downloads.",
                "powershell.exe",
                "HIGH",
                "T1059.001",
                "Command and Scripting Interpreter: PowerShell",
                "PowerShell was invoked with stealth evasion flags (such as hidden windows or encoded base64 parameters) commonly used by attackers to execute fileless memory payloads."
        );
    }

    @Override
    public boolean matches(LogEventDto event) {
        if (event == null || event.getProcessName() == null || event.getCommandLine() == null) {
            return false;
        }

        String proc = event.getProcessName().toLowerCase();
        if (!proc.contains("powershell.exe") && !proc.contains("pwsh.exe")) {
            return false;
        }

        String cmd = event.getCommandLine().toLowerCase();
        int matchCount = 0;
        for (String flag : STEALTH_FLAGS) {
            if (cmd.contains(flag)) {
                matchCount++;
            }
        }

        // Match if at least 2 stealth/evasion flags are present in the command line
        return matchCount >= 2 || cmd.contains("-encodedcommand") || cmd.contains("-enc");
    }
}
