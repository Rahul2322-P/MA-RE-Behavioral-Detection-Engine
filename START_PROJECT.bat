@echo off
title LOLBin Detection Engine - B.Tech Capstone Project
echo ======================================================================
echo   Starting Behavioral Detection Engine (Spring Boot + React + MySQL)
echo ======================================================================
echo.
echo [1/2] Connecting to local MySQL database (port 3306)...
echo [2/2] Launching Spring Boot Backend and React Frontend...
echo.
powershell.exe -NoExit -ExecutionPolicy Bypass -File "%~dp0run_app.ps1"
