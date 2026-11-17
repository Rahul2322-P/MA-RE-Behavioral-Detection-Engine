@echo off
title Behavioral Detection Engine - B.Tech Capstone Project
echo ======================================================================
echo   Starting Behavioral Detection Engine (Spring Boot + React + MySQL)
echo ======================================================================
echo.
echo [1/2] Launching Spring Boot Backend on http://localhost:8085...
start "Backend - Spring Boot" powershell -NoExit -ExecutionPolicy Bypass -Command "cd '%~dp0backend'; if (Test-Path '.maven\apache-maven-3.9.6\bin\mvn.cmd') { .\.maven\apache-maven-3.9.6\bin\mvn.cmd spring-boot:run } else { mvn spring-boot:run }"

echo [2/2] Launching React Vite Frontend on http://localhost:5173...
start "Frontend - React Vite" powershell -NoExit -ExecutionPolicy Bypass -Command "cd '%~dp0frontend'; if (-not (Test-Path 'node_modules')) { npm install }; npm run dev"

echo.
echo ======================================================================
echo   Application started successfully!
echo   - Backend API:  http://localhost:8085/api
echo   - Frontend UI:  http://localhost:5173
echo ======================================================================
