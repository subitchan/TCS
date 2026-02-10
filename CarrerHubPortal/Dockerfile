FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/CarrerHubPortal-0.0.1-SNAPSHOT.war app.war

EXPOSE 2003

ENTRYPOINT ["java", "-jar", "app.war"]
