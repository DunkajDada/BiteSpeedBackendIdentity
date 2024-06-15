# Use a base image with OpenJDK 17 (adjust as needed)
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled JAR file from the Maven target directory to the container
COPY target/BiteSpeedBackendIdentity-0.0.1-SNAPSHOT.jar .

# Expose port 8080 (assuming your application listens on this port)
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "BiteSpeedBackendIdentity-0.0.1-SNAPSHOT.jar"]
