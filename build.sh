#!/bin/bash
# Spring Boot Donation System - Build Script (Linux/macOS)
set -e
echo "Building Donation System..."
mvn clean package
echo "✅ Build successful!"
echo "Run with: java -jar target/donation-system-1.0.0.jar"
