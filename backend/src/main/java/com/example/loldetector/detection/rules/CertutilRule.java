package com.example.loldetector.detection.rules;

import com.example.loldetector.detection.DetectionRule;
import com.example.loldetector.dto.LogEventDto;
import org.springframework.stereotype.Component;

@Component
public class CertutilRule extends DetectionRule {

    public CertutilRule() {
        super(
                "RULE-CERT-001",
                "Certutil Payload Ingress & Base64 Decoding Abuse",
                "Detects certutil.exe abusing -urlcache, -split, -f, or -decode parameters to download external binary files or decode obfuscated payloads.",
                "certutil.exe",
                "HIGH",
                "T1105",
                "Ingress Tool Transfer",
                "Certutil certificate management utility was executed with download or decoding parameters (-urlcache, -split, or -decode) commonly used for defense evasion and remote binary retrieval."
        );
    }

    @Override
    public boolean matches(LogEventDto event) {
        if (event == null || event.getProcessName() == null || event.getCommandLine() == null) {
            return false;
        }

        String proc = event.getProcessName().toLowerCase();
        if (!proc.contains("certutil.exe")) {
            return false;
        }

        String cmd = event.getCommandLine().toLowerCase();

        boolean isUrlCache = cmd.contains("-urlcache") || cmd.contains("/urlcache");
        boolean isDecode = cmd.contains("-decode") || cmd.contains("/decode");
        boolean isSplitOrForce = cmd.contains("-split") || cmd.contains("/split") || cmd.contains("-f ");

        return (isUrlCache && (isSplitOrForce || cmd.contains("http"))) || isDecode;
    }
}
