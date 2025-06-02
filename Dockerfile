# Build Stage
FROM gradle:8.7-jdk21 AS build
WORKDIR /biketrust
COPY . .
RUN gradle bootJar --no-daemon

# Run Stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /biketrust
COPY --from=build /biketrust/build/libs/*.jar biketrust.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "biketrust.jar", "--spring.profiles.active=prod"]