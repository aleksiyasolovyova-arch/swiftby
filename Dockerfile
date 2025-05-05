FROM eclipse-temurin:21-jre-alpine
WORKDIR /biketrust
COPY build/libs/*.jar biketrust.jar
EXPOSE 80
ENTRYPOINT ["java", "-jar", "biketrust.jar"]
