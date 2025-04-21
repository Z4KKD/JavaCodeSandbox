# Use an official OpenJDK runtime as the base image
FROM openjdk:21-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled JAR file from your local machine to the container
COPY target/ZakksTasks-0.0.1-SNAPSHOT.jar /app/ZakksTasks.jar

# Expose the port that the Spring Boot application will run on
EXPOSE 8080

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "ZakksTasks.jar"]
