# Use a base image with Java and Maven
FROM maven:3.8.4-openjdk-17 AS build

# Set the working directory
WORKDIR /app

# Copy the Maven project file
COPY pom.xml .

# Copy the source code
COPY src ./src

# Build the application
RUN mvn package -DskipTests

# Create a new stage for the final image
FROM openjdk:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the previous stage
COPY --from=build /app/target/BiteSpeedBackendIdentity-0.0.1-SNAPSHOT.jar .

# Expose port 8080
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "BiteSpeedBackendIdentity-0.0.1-SNAPSHOT.jar"]
