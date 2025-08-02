# Changed from 'openjdk:17-jdk-slim-bullseye'
# Using jammy as it's common, or you can try bullseye if this fails
FROM eclipse-temurin:17-jdk-jammy AS build

WORKDIR /app

# Install Maven
RUN apt-get update && apt-get install -y maven \
    && rm -rf /var/lib/apt/lists/*

COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Changed from 'openjdk:17-jre-slim-jammy'
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# --- NEW LINE START HERE ---
# Corrected path: JAR is in /app/target. Using *.jar for flexibility and then giving it a fixed name.
COPY --from=build /app/target/*.jar /app/simple-microservice.jar
# --- NEW LINE END HERE ---

EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/simple-microservice.jar"]