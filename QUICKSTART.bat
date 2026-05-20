@echo off
REM Donation System - Quick Start Guide (Windows)

echo.
echo === Donation System - Spring Boot Web Application ===
echo.
echo Prerequisites:
echo - Java 17 or later: java -version
echo - Maven 3.6+: mvn -version
echo - Docker: docker --version
echo.

echo --- LOCAL DEVELOPMENT ---
echo 1. Build the project:
echo    mvn clean package
echo.
echo 2. Run the application:
echo    java -jar target\donation-system-1.0.0.jar
echo.
echo 3. Open browser:
echo    http://localhost:8080
echo.

echo --- DOCKER DEPLOYMENT (Local) ---
echo 1. Build Docker image:
echo    docker build -t donation-app .
echo.
echo 2. Run container:
echo    docker run -p 8080:8080 --rm donation-app
echo.
echo 3. Access:
echo    http://localhost:8080
echo.

echo --- AWS EC2 DEPLOYMENT ---
echo 1. SSH into EC2 instance:
echo    ssh -i your-key.pem ec2-user@your-ec2-ip
echo.
echo 2. Install Docker:
echo    sudo yum update -y
echo    sudo yum install docker -y
echo    sudo systemctl start docker
echo    sudo usermod -aG docker ec2-user
echo.
echo 3. Clone repository:
echo    git clone https://github.com/Shyampulluri/jdbc-donation-system.git
echo    cd jdbc-donation-system
echo.
echo 4. Build and run:
echo    docker build -t donation-app .
echo    docker run -d -p 8080:8080 --name donation donation-app
echo.
echo 5. Access application:
echo    http://your-ec2-public-ip:8080
echo.

echo --- TROUBLESHOOTING ---
echo Port 8080 already in use:
echo   docker run -p 8081:8080 donation-app
echo.
echo View container logs:
echo   docker logs -f container-id
echo.

echo ✅ See README.md for full documentation
pause
