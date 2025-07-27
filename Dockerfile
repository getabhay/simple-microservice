FROM maven:3.8.8-openjdk-17 as build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn -B -V clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=build /target/simple-microservice.jar /app/simple-microservice.jar

EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/simple-microservice.jar"]
