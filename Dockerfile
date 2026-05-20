FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY . .

# Compile with H2 jar on Linux
RUN javac -cp ".:h2.jar" DBConnection.java StudentForm.java

# Run the Swing app (requires desktop environment or GUI forwarding)
CMD ["java", "-cp", ".:h2.jar", "StudentForm"]