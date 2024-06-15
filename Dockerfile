# Use a base image with Java and Maven for building the application
FROM maven:3.8.4-openjdk-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project file (pom.xml) to the container
COPY pom.xml .

# Copy the source code directory (src) to the container
COPY src ./src

# Build the application using Maven (skip tests during build)
RUN mvn package -DskipTests

# Create a new stage for the final image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the build stage to the final image
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar .

# Expose port 8080 (assuming your application listens on this port)
EXPOSE 8080

# Command to run the application when the container starts
CMD ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]
