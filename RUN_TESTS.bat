@echo off
title Running JUnit 5 Test Suite - LOLBin Behavioral Detection Engine
echo ======================================================================
echo   Executing Automated Unit & Integration Tests (11 Test Cases)
echo ======================================================================
echo.
cd /d "%~dp0backend"
if exist ".maven\apache-maven-3.9.6\bin\mvn.cmd" (
    call ".maven\apache-maven-3.9.6\bin\mvn.cmd" test
) else (
    call mvn test
)
echo.
echo ======================================================================
echo   Test Execution Complete!
echo ======================================================================
pause
