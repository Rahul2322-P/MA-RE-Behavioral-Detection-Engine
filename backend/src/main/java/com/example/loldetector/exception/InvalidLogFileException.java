package com.example.loldetector.exception;

public class InvalidLogFileException extends RuntimeException {
    public InvalidLogFileException(String message) {
        super(message);
    }

    public InvalidLogFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
