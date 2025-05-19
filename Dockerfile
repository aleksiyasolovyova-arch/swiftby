#Build Stage
FROM gradle:8.7-jdk21 AS build
WORKDIR /biketrust
COPY . .
RUN gradle bootJar --no-daemon

#Run Stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /biketrust
COPY build/libs/*.jar biketrust.jar
EXPOSE 80
ENTRYPOINT ["java", "-jar", "biketrust.jar"]
