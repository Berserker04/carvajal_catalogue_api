FROM openjdk:17-jdk-alpine
COPY build/libs/catalogue-0.0.1-SNAPSHOT.jar carvajal-api.jar
ENTRYPOINT ["java", "-jar", "carvajal-api.jar"]