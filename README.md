# BikeTrust

## Team members:
Aleksiya Soloyova, Alina Dimova, MJ Jalloh, Storm van Loon, Daniil Mumladze

## Running the project:
Running the docker compose file:
```
docker-compose up -d
```
Running the project itself includes 2 steps:
1. To build the project: ```./gradlew build```
2. To run the jar file(that was built in step 1): ```java java -jar build/libs/your-app.jar```

You can also directly run the project using bootrun:
```./gradlew bootRun```