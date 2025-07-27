FROM openjdk:17-jdk-slim as build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:17-jre-slim

WORKDIR /app

COPY --from=build /target/simple-microservice.jar /app/simple-microservice.jar

EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/simple-microservice.jar"]
