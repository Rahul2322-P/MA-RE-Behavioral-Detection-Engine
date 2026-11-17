# PowerShell Launch Script for Behavioral Detection Engine Capstone Application
# This script starts both the Spring Boot Backend and the React Frontend on Windows

Write-Host "======================================================================" -ForegroundColor Cyan
Write-Host "  Starting Behavioral Detection Engine (B.Tech Capstone Project)     " -ForegroundColor Cyan
Write-Host "======================================================================" -ForegroundColor Cyan

# Step 1: Start Spring Boot Backend in a new PowerShell window
Write-Host "[1/2] Launching Spring Boot Backend on http://localhost:8085..." -ForegroundColor Yellow
$backendScript = "$PSScriptRoot\backend\run_backend.ps1"
Start-Process powershell -ArgumentList "-NoExit", "-ExecutionPolicy", "Bypass", "-File", "$backendScript"

# Step 2: Start React Frontend in a new PowerShell window
Write-Host "[2/2] Launching React Vite Frontend on http://localhost:5173..." -ForegroundColor Yellow
$frontendCommand = "cd '$PSScriptRoot\frontend'; if (-not (Test-Path 'node_modules')) { Write-Host 'Installing frontend npm packages...' -ForegroundColor Cyan; npm install }; npm run dev"
Start-Process powershell -ArgumentList "-NoExit", "-Command", "$frontendCommand"

Write-Host ""
Write-Host "Both applications are launching in separate terminal windows!" -ForegroundColor Green
Write-Host " - Backend API:  http://localhost:8085/api" -ForegroundColor White
Write-Host " - Frontend UI:   http://localhost:5173" -ForegroundColor White
Write-Host "======================================================================" -ForegroundColor Cyan
