# Use an official OpenJDK runtime as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven build output (JAR file) to the container
COPY target/online-book-store-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application runs on (default for Spring Boot is 8080)
EXPOSE 8080

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]