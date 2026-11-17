@echo off
title Push MA&RE Project to GitHub (Rahul2322-P)
echo ======================================================================
echo   Pushing Code to https://github.com/Rahul2322-P/MA-RE-Behavioral-Detection-Engine
echo ======================================================================
echo.

cd /d "%~dp0"

echo [1/3] Staging clean source files...
git add .

echo.
echo [2/3] Checking commits...
git commit -m "feat: complete MA&RE LOLBin behavioral detection engine capstone project" >nul 2>&1

echo.
echo [3/3] Pushing to GitHub (Rahul2322-P)...
git push -u origin main

if %errorlevel% neq 0 (
    echo.
    echo [!] If you see 'Repository not found':
    echo     Please create the repository named 'MA-RE-Behavioral-Detection-Engine'
    echo     at https://github.com/new under your account (Rahul2322-P).
    echo.
    echo Retrying push...
    git push -u origin main --force
)

echo.
echo ======================================================================
echo   Check your GitHub repo:
echo   https://github.com/Rahul2322-P/MA-RE-Behavioral-Detection-Engine
echo ======================================================================
pause
