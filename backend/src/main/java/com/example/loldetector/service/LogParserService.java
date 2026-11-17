package com.example.loldetector.service;

import com.example.loldetector.dto.LogEventDto;
import com.example.loldetector.exception.InvalidLogFileException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class LogParserService {

    private final ObjectMapper objectMapper;

    public LogParserService() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    public List<LogEventDto> parseLogFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new InvalidLogFileException("Uploaded log file is empty or missing.");
        }

        String fileName = file.getOriginalFilename();
        if (fileName != null && !fileName.toLowerCase().endsWith(".json")) {
            throw new InvalidLogFileException("Invalid file format. Only JSON log files are supported.");
        }

        try (InputStream inputStream = file.getInputStream()) {
            List<LogEventDto> rawEvents = objectMapper.readValue(inputStream, new TypeReference<List<LogEventDto>>() {});
            List<LogEventDto> validEvents = new ArrayList<>();

            for (LogEventDto event : rawEvents) {
                if (validateAndSanitizeEvent(event)) {
                    validEvents.add(event);
                }
            }

            if (validEvents.isEmpty()) {
                throw new InvalidLogFileException("Log file contains no valid process log records.");
            }

            return validEvents;
        } catch (InvalidLogFileException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidLogFileException("Failed to parse JSON log file: " + e.getMessage(), e);
        }
    }

    private boolean validateAndSanitizeEvent(LogEventDto event) {
        if (event.getProcessName() == null || event.getProcessName().isBlank()) {
            return false;
        }
        if (event.getCommandLine() == null || event.getCommandLine().isBlank()) {
            return false;
        }

        if (event.getEventId() == null || event.getEventId().isBlank()) {
            event.setEventId("EVT-" + System.currentTimeMillis() + "-" + (int)(Math.random() * 1000));
        }

        if (event.getTimestamp() == null) {
            event.setTimestamp(LocalDateTime.now());
        }

        if (event.getParentProcessName() == null) {
            event.setParentProcessName("unknown.exe");
        }

        if (event.getUser() == null) {
            event.setUser("SYSTEM");
        }

        return true;
    }
}
