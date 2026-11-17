@echo off
title Behavioral Detection Engine - B.Tech Capstone Project
echo ======================================================================
echo   Starting Behavioral Detection Engine (Spring Boot + React)
echo ======================================================================
echo.
echo [1/2] Launching Spring Boot Backend on http://localhost:8085...
start "Backend - Spring Boot" powershell -NoExit -ExecutionPolicy Bypass -File "%~dp0backend\run_backend.ps1"

echo [2/2] Launching React Vite Frontend on http://localhost:5173...
start "Frontend - React Vite" powershell -NoExit -ExecutionPolicy Bypass -Command "cd '%~dp0frontend'; if (-not (Test-Path 'node_modules')) { npm install }; npm run dev"

echo.
echo ======================================================================
echo   Both services are starting!
echo   - Backend API:  http://localhost:8085/api
echo   - Frontend UI:  http://localhost:5173
echo ======================================================================
