# Use a base image with Java and Maven
FROM maven:3.8.4-openjdk-17

# Set the working directory
WORKDIR /app

# Copy the Maven project file
COPY pom.xml .

# Copy the source code
COPY src ./src

# Build the application
RUN mvn package -DskipTests

# Set the working directory to target
WORKDIR /app/target

# Expose port 8080
EXPOSE 8080

# Define the command to run the application
CMD ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]
