FROM openjdk:11
COPY hy-notice-api-1.0-SNAPSHOT.jar api-app.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "-Dspring.datasource.username=${DATASOURCE_USERNAME}", "-Dspring.datasource.password=${DATASOURCE_PASSWORD}","api-app.jar"]
