package com.example.loldetector.detection.rules;

import com.example.loldetector.detection.DetectionRule;
import com.example.loldetector.dto.LogEventDto;
import org.springframework.stereotype.Component;

@Component
public class WmiRule extends DetectionRule {

    public WmiRule() {
        super(
                "RULE-WMI-001",
                "WMI Remote Execution & Shadow Copy Deletion",
                "Detects suspicious WMI command-line utility usage to delete Volume Shadow Copies or execute secondary processes remotely.",
                "wmic.exe",
                "CRITICAL",
                "T1047",
                "Windows Management Instrumentation",
                "WMI utility (wmic.exe) was executed with parameters to delete Volume Shadow Copies (inhibiting system recovery) or spawn secondary concealed processes."
        );
    }

    @Override
    public boolean matches(LogEventDto event) {
        if (event == null || event.getProcessName() == null || event.getCommandLine() == null) {
            return false;
        }

        String proc = event.getProcessName().toLowerCase();
        if (!proc.contains("wmic.exe")) {
            return false;
        }

        String cmd = event.getCommandLine().toLowerCase();

        boolean isShadowDelete = cmd.contains("shadowcopy") && cmd.contains("delete");
        boolean isProcessCreate = cmd.contains("process") && cmd.contains("call") && cmd.contains("create");
        boolean isRemoteNode = cmd.contains("/node:");

        return isShadowDelete || isProcessCreate || isRemoteNode;
    }
}
