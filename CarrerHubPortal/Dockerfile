# -------- STAGE 1: Build the app --------
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /build
COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# -------- STAGE 2: Run the app --------
FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY --from=build /build/target/*.war app.war

EXPOSE 2003

ENTRYPOINT ["java", "-jar", "app.war"]
