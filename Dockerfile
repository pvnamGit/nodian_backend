# Dockerfile

# Build stage
FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .

RUN ./gradlew bootJar --no-daemon

# Production stage
FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=build /build/libs/nodian_backend-1.jar app.jar

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]