# Use an official Java runtime as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file into the container
COPY target/Shopping-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the port your application runs on
EXPOSE 9095


# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
