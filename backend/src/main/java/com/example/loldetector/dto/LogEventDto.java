package com.example.loldetector.dto;

import java.time.LocalDateTime;

public class LogEventDto {

    private String eventId;
    private LocalDateTime timestamp;
    private String processName;
    private String parentProcessName;
    private String commandLine;
    private String user;
    private String processPath;

    public LogEventDto() {}

    public LogEventDto(String eventId, LocalDateTime timestamp, String processName, 
                       String parentProcessName, String commandLine, String user, String processPath) {
        this.eventId = eventId;
        this.timestamp = timestamp;
        this.processName = processName;
        this.parentProcessName = parentProcessName;
        this.commandLine = commandLine;
        this.user = user;
        this.processPath = processPath;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getParentProcessName() {
        return parentProcessName;
    }

    public void setParentProcessName(String parentProcessName) {
        this.parentProcessName = parentProcessName;
    }

    public String getCommandLine() {
        return commandLine;
    }

    public void setCommandLine(String commandLine) {
        this.commandLine = commandLine;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getProcessPath() {
        return processPath;
    }

    public void setProcessPath(String processPath) {
        this.processPath = processPath;
    }
}
