FROM eclipse-temurin:17-jdk-jammy

RUN apt-get update \
    && apt-get install -y xvfb \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY . .

# Compile with H2 jar on Linux
RUN javac -cp ".:h2.jar" DBConnection.java StudentForm.java

ENV DISPLAY=:99
CMD ["xvfb-run", "--auto-servernum", "--server-args=-screen 0 1024x768x24", "java", "-cp", ".:h2.jar", "StudentForm"]