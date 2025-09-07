# Use official OpenJDK image as base
FROM openjdk:24-jdk-slim

# Set working directory inside container
WORKDIR /app

# Copy jar file into container
COPY target/*.jar app.jar

# Expose port (same as your Spring Boot server.port)
EXPOSE 8080

# Run jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
