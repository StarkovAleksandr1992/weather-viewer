FROM maven:3.9.6-eclipse-temurin-21 as builder
WORKDIR /app/weather-viewer
COPY . /app/weather-viewer
RUN mvn -f /app/weather-viewer/pom.xml clean package

FROM eclipse-temurin:21
WORKDIR /app/weather-viewer
COPY --from=builder /app/weather-viewer/target/*.jar /app/weather-viewer/application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/weather-viewer/application.jar"]