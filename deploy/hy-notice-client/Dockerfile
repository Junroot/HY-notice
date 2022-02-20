FROM openjdk:11
COPY hy-notice-client-1.0-SNAPSHOT.jar client-app.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "-Dspring.datasource.username=${DATASOURCE_USERNAME}", "-Dspring.datasource.password=${DATASOURCE_PASSWORD}", "client-app.jar"]
