#!/bin/bash
set -e
javac -cp ".:h2.jar" DBConnection.java StudentForm.java
jar --create --file donation-app.jar --manifest manifest.mf DBConnection.class StudentForm.class StudentForm$QRPanel.class
echo "Built donation-app.jar"
