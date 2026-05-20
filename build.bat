@echo off
REM Spring Boot Donation System - Build Script (Windows)
echo Building Donation System...
mvn clean package
if %errorlevel% neq 0 (
    echo Build failed!
    pause
    exit /b 1
)
echo Build successful! Run with: java -jar target\donation-system-1.0.0.jar
pause
