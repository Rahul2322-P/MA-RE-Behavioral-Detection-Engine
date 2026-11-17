package com.example.loldetector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LolDetectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(LolDetectorApplication.class, args);
        System.out.println("=================================================");
        System.out.println("  Behavioral Detection Engine Started Successfully!");
        System.out.println("  REST API Base URL: http://localhost:8085/api");
        System.out.println("=================================================");
    }
}
