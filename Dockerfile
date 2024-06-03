FROM openjdk:21-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged JAR file into the container
COPY ./target/lab-master-v1.0-0.0.1-SNAPSHOT.jar /app/lab-master-v1.0-0.0.1-SNAPSHOT.jar

# Command to run the application
CMD ["java", "-jar", "lab-master-v1.0-0.0.1-SNAPSHOT.jar"]



