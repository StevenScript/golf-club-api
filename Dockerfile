# ----- Stage 1: Build Stage -----
FROM maven:3.9.0-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# ----- Stage 2: Runtime Stage -----
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
# Copy the built JAR from the build stage.
# This uses a wildcard; if there is exactly one jar file in target, it should work.
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]